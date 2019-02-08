package com.restaurant.management.service;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.Mail;
import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.request.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.Stream;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class AccountUserService implements UserDetailsService {

    private AuthenticationManager authenticationManager;
    private AccountUserRepository accountUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private Utils utils;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public AccountUserService(AuthenticationManager authenticationManager, AccountUserRepository userRepository,
                              RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                              JwtTokenProvider tokenProvider, Utils utils, SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.accountUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.utils = utils;
        this.simpleEmailService = simpleEmailService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        AccountUser adminUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        return UserPrincipal.create(adminUser);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserByUserUniqueId(String userUniqueId) {
        AccountUser adminUser = accountUserRepository.findAdminUserByUserUniqueId(userUniqueId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.UNIQUE_ID_NOT_FOUND.getErrorMessage()+ userUniqueId));

        return UserPrincipal.create(adminUser);
    }


    public AccountUser registerAdminAccount(SignUpUserRequest signUpUserRequest) {
       // Email and Username validation

        if(accountUserRepository.existsByUsername(signUpUserRequest.getUsername())) {
            throw new UserExistsException(UserMessages.USERNAME_TAKEN.getErrorMessage());
        }

        if(accountUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }

        AccountUser newAdmin = new AccountUser();

        String userUniqueId = utils.generateUserUniqueId(10);
        String token = tokenProvider.generateEmailVerificationToken(userUniqueId);

        newAdmin.setName(signUpUserRequest.getName());
        newAdmin.setLastname(signUpUserRequest.getLastname());
        newAdmin.setUsername(signUpUserRequest.getUsername());
        newAdmin.setEmail(signUpUserRequest.getEmail());
        newAdmin.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        newAdmin.setActive(false);
        newAdmin.setUserUniqueId(userUniqueId);

        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getErrorMessage()));

        newAdmin.setRoles(Collections.singleton(userRole));
        newAdmin.setEmailVerificationToken(token);

        accountUserRepository.save(newAdmin);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName(), signUpUserRequest.getUsername()), token);

        return newAdmin;
    }


    public AccountUser registerUserAccount(SignUpUserRequest signUpUserRequest) {
        // Email and Username validation

        if(accountUserRepository.existsByUsername(signUpUserRequest.getUsername())) {
            throw new UserExistsException(UserMessages.USERNAME_TAKEN.getErrorMessage());
        }

        if(accountUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }

        AccountUser accountUser = new AccountUser();

        String userUniqueId = utils.generateUserUniqueId(10);
        String token = tokenProvider.generateEmailVerificationToken(userUniqueId);

        accountUser.setName(signUpUserRequest.getName());
        accountUser.setLastname(signUpUserRequest.getLastname());
        accountUser.setUsername(signUpUserRequest.getUsername());
        accountUser.setEmail(signUpUserRequest.getEmail());
        accountUser.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        accountUser.setActive(false);
        accountUser.setUserUniqueId(userUniqueId);

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getErrorMessage()));

        accountUser.setRoles(Collections.singleton(userRole));
        accountUser.setEmailVerificationToken(token);

        accountUserRepository.save(accountUser);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName(), signUpUserRequest.getUsername()), token);

        return accountUser;
    }



    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        // If User is Active validation
        if (!accountUser.getActive()) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean requestResetPassword(String usernameOrEmail) {
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        Stream.of(accountUser).forEach(u -> {
            if (!u.getActive()) {
                throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
            }
             u.setPasswordResetToken(tokenProvider.generatePasswordResetToken(u.getUserUniqueId()));
             accountUserRepository.save(u);

             simpleEmailService.sendResetPasswordEmail(
                    new Mail(u.getEmail(), u.getName(), u.getUsername()), u.getPasswordResetToken());
        });

        return true;
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        AccountUser adminUser = accountUserRepository.findAdminUserByEmailVerificationToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        if (!hasTokenExpired) {
            adminUser.setEmailVerificationToken(null);
            adminUser.setActive(Boolean.TRUE);
            accountUserRepository.save(adminUser);
            returnValue = true;
        }
        return returnValue;
    }

    public boolean resetPassword(String token, PasswordReset passwordReset) {
        boolean returnValue = false;
        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        AccountUser accountUser = accountUserRepository.findAdminUserByPasswordResetToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        if (!accountUser.getActive()) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
        }

        if (!passwordReset.getPassword().equals(passwordReset.getConfirmPassword())) {
            throw new UserAuthenticationException(UserMessages.PASSWORDS_EQUALS.getErrorMessage());
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

}