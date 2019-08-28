package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDTO;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.mapper.RoleMapper;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.SimpleEmailService;
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
public class AccountUserServiceImpl implements AccountUserService {

    private AuthenticationManager authenticationManager;
    private AccountUserRepository accountUserRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public AccountUserServiceImpl(AuthenticationManager authenticationManager, AccountUserRepository userRepository,
                                  PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider,
                                  SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.accountUserRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.simpleEmailService = simpleEmailService;
    }

    private AccountUser getByEmail(String email) {
        return accountUserRepository.findByEmailAndIsDeletedIsFalse(email)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage() + email));
    }

    public Company getCompany(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return accountUser.getCompany();
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
        AccountUser accountUser = getByEmail(email);

        return UserPrincipal.create(accountUser);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserByUserId(Long id) {
        AccountUser accountUser = getUserById(id);

        return UserPrincipal.create(accountUser);
    }

    public ApiResponse checkEmailAvailabilityInCompany(@CurrentUser UserPrincipal currentUser, String email) {
        Long companyId = getCompany(currentUser).getId();

        if (accountUserRepository.existsByEmailAndCompanyIdAndIsDeletedIsFalse(email, companyId)){
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    public String getRoleToString(RoleName roleName) {
        return RoleMapper.mapRoleToString(roleName);
    }

    public AccountUser getUserById(Long id) {
        return accountUserRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    public AccountUser getCompanyUserById(@CurrentUser UserPrincipal currentUser, Long id) {
        AccountUser currentUserResult = getUserById(currentUser.getId());

        Long companyId = currentUserResult.getCompany().getId();

        return accountUserRepository.findByIdAndCompanyIdAndIsDeletedIsFalse(id, companyId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        AccountUser accountUser = getByEmail(loginRequest.getEmail());

        checkAccountActivationStatus(accountUser.isActive());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean requestResetPassword(String email) {
        AccountUser accountUser = getByEmail(email);

        checkAccountActivationStatus(accountUser.isActive());

        Stream.of(accountUser).forEach(u -> {

            u.setPasswordResetToken(tokenProvider.generatePasswordResetToken(u.getId()));

            accountUserRepository.save(u);

            simpleEmailService.sendResetPasswordEmail(
                    new Mail(u.getEmail(), u.getName()), u.getPasswordResetToken());
        });
        return true;
    }

    public boolean resendEmailVerificationToken(String email) {
        AccountUser accountUser = getByEmail(email);

        String token = accountUser.getEmailVerificationToken();

        simpleEmailService.sendEmailVerification(
                new Mail(accountUser.getEmail(), accountUser.getName()), token);

        return true;
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        AccountUser accountUser = accountUserRepository.findUserByEmailVerificationToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getMessage()));

        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        if (!hasTokenExpired) {

            accountUser.setEmailVerificationToken(null);
            accountUser.setActive(Boolean.TRUE);

            accountUserRepository.save(accountUser);

            returnValue = true;
        }
        return returnValue;
    }

    public boolean resetPassword(String token, PasswordReset passwordReset) {
        boolean returnValue = false;
        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        AccountUser accountUser = accountUserRepository.findUserByPasswordResetToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getMessage()));

        checkAccountActivationStatus(accountUser.isActive());

        if (!passwordReset.getPassword().equals(passwordReset.getConfirmPassword())) {
            throw new UserAuthenticationException(UserMessages.PASSWORDS_EQUALS.getMessage());
        }

        if (!hasTokenExpired) {
            String encodedPassword = passwordEncoder.encode(passwordReset.getPassword());

            accountUser.setPassword(encodedPassword);
            accountUser.setPasswordResetToken(null);

            accountUserRepository.save(accountUser);

            returnValue = true;
        }
        return returnValue;
    }

    public AccountUser updateUserDetails(@CurrentUser UserPrincipal currentUser, UserUpdateRequest request) {
        AccountUser accountUser = getUserById(currentUser.getId());

        accountUser.setName(request.getUserDetails().getName());
        accountUser.setLastName(request.getUserDetails().getLastName());
        accountUser.setPhone(request.getUserDetails().getPhone());

        accountUserRepository.save(accountUser);

        return accountUser;
    }

    public AccountUser updateAccountInfo(@CurrentUser UserPrincipal currentUser, AccountUserDTO accountUserDTO) {
        AccountUser accountUser = getCompanyUserById(currentUser, currentUser.getId());

        if (!accountUser.getEmail().equals(accountUserDTO.getEmail())) {
            checkEmailAvailabilityInCompany(currentUser, accountUserDTO.getEmail());
        }

        if (!accountUserDTO.getPhone().isEmpty()) {
            validatePhoneNumberFormat(accountUserDTO.getPhone());
        }

        Stream.of(accountUser)
                .forEach(acc -> {
                    acc.setName(accountUserDTO.getName());
                    acc.setLastName(accountUserDTO.getLastName());
                    acc.setEmail(accountUserDTO.getEmail());
                    acc.setPhone(accountUserDTO.getPhone());
                    acc.setJobTitle(accountUserDTO.getJobTitle());
                    acc.getAccountUserAddress().setStreetAndNumber(accountUserDTO.getStreetAndNumber());
                    acc.getAccountUserAddress().setPostCode(accountUserDTO.getPostCode());
                    acc.getAccountUserAddress().setCity(accountUserDTO.getCity());
                    acc.getAccountUserAddress().setCountry(accountUserDTO.getCountry());
                });

        accountUserRepository.save(accountUser);

        return accountUser;
    }

}