package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.RestaurantInfoRepository;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.SimpleEmailService;
import com.restaurant.management.web.request.account.LoginRequest;
import com.restaurant.management.web.request.account.SignUpUserRequest;
import com.restaurant.management.web.request.account.UpdateAccountInfo;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.request.account.PasswordReset;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.Stream;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class AccountUserServiceImpl implements AccountUserService {

    private AuthenticationManager authenticationManager;
    private AccountUserRepository accountUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public AccountUserServiceImpl(AuthenticationManager authenticationManager, AccountUserRepository userRepository,
                                  RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                  JwtTokenProvider tokenProvider,  SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.accountUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.simpleEmailService = simpleEmailService;
    }

    private AccountUser getByEmail(String email) {
        return accountUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage() + email));
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

    public ApiResponse checkEmailAvailability(String email) {
        if(accountUserRepository.existsByEmail(email)) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    public AccountUser registerAdminAccount(SignUpUserRequest signUpUserRequest) {

        checkEmailAvailability(signUpUserRequest.getEmail());

        String token = tokenProvider.generateEmailVerificationToken(signUpUserRequest.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        AccountUser newAdminUser = new AccountUser.AccountUserBuilder()
                .setName(signUpUserRequest.getName())
                .setLastname(signUpUserRequest.getLastname())
                .setEmail(signUpUserRequest.getEmail())
                .setPhoneNumber(signUpUserRequest.getPhoneNumber())
                .setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()))
                .setIsActive(Boolean.FALSE)
                .setRoles(Collections.singleton(userRole))
                .setEmailVerificationToken(token)
                .build();

        accountUserRepository.save(newAdminUser);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName()), token);

        return newAdminUser;
    }

    public AccountUser registerManagerAccount(SignUpUserRequest signUpUserRequest) {

        checkEmailAvailability(signUpUserRequest.getEmail());

        String token = tokenProvider.generateEmailVerificationToken(signUpUserRequest.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        /**
         *  Temporary changed Active to TRUE and EmailVerificationToken to NULL
         *  Disabled email sending
         *  Change it back to production version
         */

        AccountUser accountUser = new AccountUser.AccountUserBuilder()
                .setName(signUpUserRequest.getName())
                .setLastname(signUpUserRequest.getLastname())
                .setEmail(signUpUserRequest.getEmail())
                .setPhoneNumber(signUpUserRequest.getPhoneNumber())
                .setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()))
                .setIsActive(Boolean.TRUE)
                .setRoles(Collections.singleton(userRole))
                .setEmailVerificationToken(null)
                .build();

        accountUserRepository.save(accountUser);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName()), token);

        return accountUser;
    }

    public RoleName[] getRoles() {
       return   RoleName.values();
    }

    public ApiResponse deleteUserById(Long id) {
        AccountUser accountUser = getUserById(id);

        accountUserRepository.deleteById(accountUser.getId());

        return new ApiResponse(true, UserMessages.ACCOUNT_DELETED.getMessage());
    }

    public AccountUser updateAccountInfo(@CurrentUser UserPrincipal currentUser, UpdateAccountInfo request) {

        AccountUser accountUser = getUserById(currentUser.getId());

        Stream.of(accountUser).forEach(acc -> {
            acc.setName(request.getName());
            acc.setLastname(request.getLastname());
            acc.setPhoneNumber(request.getPhoneNumber());
            accountUserRepository.save(acc);
        });

        return accountUser;
    }

    public AccountUser getUserById(Long id) {
        return accountUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    public AccountUser getRestaurantUserById(@CurrentUser UserPrincipal currentUser, Long id) {
        AccountUser currentUserResult = getUserById(currentUser.getId());

        Long restaurantId = currentUserResult.getRestaurantInfo().getId();

        return accountUserRepository.findByIdAndRestaurantInfoId(id, restaurantId)
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

    public Page<AccountUser> getAllAccountUsers(Pageable pageable) {
        return accountUserRepository.findAll(pageable);
    }

    public Page<AccountUser> getRestaurantUsers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUserById(currentUser.getId());

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return accountUserRepository.findAllByRestaurantInfoId(restaurantId, pageable);
    }

}