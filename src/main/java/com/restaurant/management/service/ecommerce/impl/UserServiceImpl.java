package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.UserDTO;
import com.restaurant.management.exception.ecommerce.user.*;
import com.restaurant.management.mapper.ecommerce.RoleMapper;
import com.restaurant.management.repository.ecommerce.UserRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.UserService;
import com.restaurant.management.service.ecommerce.SimpleEmailService;
import com.restaurant.management.web.request.user.*;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

import static com.restaurant.management.utils.Validation.validatePhoneNumberFormat;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class UserServiceImpl implements UserService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider,
                           SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.simpleEmailService = simpleEmailService;
    }

    private User getByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedIsFalse(email)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage() + email));
    }

    public Company getCompany(@CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return user.getCompany();
    }

    private boolean checkAccountActivationStatus(Boolean status) {
        if (!status) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getMessage());
        }
        return true;
    }

    //This method is necessary for spring security context
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = getByEmail(email);

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserByUserId(Long id) {
        User user = getUserById(id);

        return UserPrincipal.create(user);
    }

    public ApiResponse checkEmailAvailability(String email) {
        if(userRepository.existsByEmailAndIsDeletedIsFalse(email)) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    public ApiResponse checkEmailAvailabilityInCompany(@CurrentUser UserPrincipal currentUser, String email) {
        Long companyId = getCompany(currentUser).getId();

        if (userRepository.existsByEmailAndCompanyIdAndIsDeletedIsFalse(email, companyId)){
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    public String getRoleToString(RoleName roleName) {
        return RoleMapper.mapRoleToString(roleName);
    }

    public User getUserById(Long id) {
        return userRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    public User getCompanyUserById(@CurrentUser UserPrincipal currentUser, Long id) {
        User currentUserResult = getUserById(currentUser.getId());

        Long companyId = currentUserResult.getCompany().getId();

        return userRepository.findByIdAndCompanyIdAndIsDeletedIsFalse(id, companyId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        User user = getByEmail(loginRequest.getEmail());

        checkAccountActivationStatus(user.isActive());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean requestResetPassword(String email) {
        User user = getByEmail(email);

        checkAccountActivationStatus(user.isActive());

        Stream.of(user).forEach(u -> {

            u.setPasswordResetToken(tokenProvider.generatePasswordResetToken(u.getId()));

            userRepository.save(u);

            simpleEmailService.sendResetPasswordEmail(
                    new Mail(u.getEmail(), u.getName()), u.getPasswordResetToken());
        });
        return true;
    }

    public boolean resendEmailVerificationToken(String email) {
        User user = getByEmail(email);

        String token = user.getEmailVerificationToken();

        simpleEmailService.sendEmailVerification(
                new Mail(user.getEmail(), user.getName()), token);

        return true;
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        User user = userRepository.findUserByEmailVerificationToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getMessage()));

        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        if (!hasTokenExpired) {

            user.setEmailVerificationToken(null);
            user.setActive(Boolean.TRUE);

            userRepository.save(user);

            returnValue = true;
        }
        return returnValue;
    }

    public ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, NewPasswordRequest request) {
        User user = getUserById(userPrincipal.getId());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            throw new UserBadRequestException(UserMessages.ACCESS_DENIED.getMessage());

        } else if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {

            throw new UserBadRequestException(UserMessages.DIFFERENT_PASSWORD.getMessage());

        } else if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {

            throw new UserBadRequestException(UserMessages.PASSWORDS_EQUALS.getMessage());
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());

        user.setPassword(encodedPassword);
        userRepository.save(user);

        return new ApiResponse(true, UserMessages.PASSWORD_CHANGED.getMessage());
    }

    public boolean resetPassword(String token, PasswordReset passwordReset) {
        boolean returnValue = false;
        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        User user = userRepository.findUserByPasswordResetToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getMessage()));

        checkAccountActivationStatus(user.isActive());

        if (!passwordReset.getPassword().equals(passwordReset.getConfirmPassword())) {
            throw new UserAuthenticationException(UserMessages.PASSWORDS_EQUALS.getMessage());
        }

        if (!hasTokenExpired) {
            String encodedPassword = passwordEncoder.encode(passwordReset.getPassword());

            user.setPassword(encodedPassword);
            user.setPasswordResetToken(null);

            userRepository.save(user);

            returnValue = true;
        }
        return returnValue;
    }

    public User updateAccountInfo(@CurrentUser UserPrincipal currentUser, UserDTO userDTO) {
        User user = getCompanyUserById(currentUser, currentUser.getId());

        if (!user.getEmail().equals(userDTO.getEmail())) {
            checkEmailAvailabilityInCompany(currentUser, userDTO.getEmail());
        }

        if (!userDTO.getPhone().isEmpty()) {
            validatePhoneNumberFormat(userDTO.getPhone());
        }

        Stream.of(user)
                .forEach(acc -> {
                    acc.setName(userDTO.getName());
                    acc.setLastName(userDTO.getLastName());
                    acc.setEmail(userDTO.getEmail());
                    acc.setPhone(userDTO.getPhone());
                    acc.setJobTitle(userDTO.getJobTitle());
                    acc.getUserAddress().setStreetAndNumber(userDTO.getStreetAndNumber());
                    acc.getUserAddress().setPostCode(userDTO.getPostCode());
                    acc.getUserAddress().setCity(userDTO.getCity());
                    acc.getUserAddress().setCountry(userDTO.getCountry());
                });

        userRepository.save(user);

        return user;
    }

}