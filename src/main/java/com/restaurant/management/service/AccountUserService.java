package com.restaurant.management.service;

import com.restaurant.management.domain.Mail;
import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.repository.AdminUserRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.Stream;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class AdminUserService extends AbstractUserDetailsService {

    private AuthenticationManager authenticationManager;
    private AdminUserRepository adminUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private Utils utils;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public AdminUserService(AuthenticationManager authenticationManager, AdminUserRepository userRepository,
                            RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                            JwtTokenProvider tokenProvider, Utils utils, SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.adminUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.utils = utils;
        this.simpleEmailService = simpleEmailService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        AdminUser adminUser = adminUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        return UserPrincipal.create(adminUser);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserByUserUniqueId(String userUniqueId) {
        AdminUser adminUser = adminUserRepository.findAdminUserByUserUniqueId(userUniqueId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.UNIQUE_ID_NOT_FOUND.getErrorMessage()+ userUniqueId));

        return UserPrincipal.create(adminUser);
    }


    public AdminUser createAdmin(SignUpUserRequest signUpUserRequest) {
       /* Email and Username validation

        if(adminUserRepository.existsByUsername(signUpUserRequest.getUsername())) {
            throw new UserExistsException(UserMessages.USERNAME_TAKEN.getErrorMessage());
        }

        if(adminUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }
        */
        AdminUser newAdmin = new AdminUser();

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

        adminUserRepository.save(newAdmin);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName(), signUpUserRequest.getUsername()), token);

        return newAdmin;
    }


    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        AdminUser adminUser = adminUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        /* If User is Active validation
        if (!adminUser.getActive()) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
        }
        */

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean requestResetPassword(String usernameOrEmail) {
        AdminUser adminUser = adminUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        Stream.of(adminUser).forEach(u -> {
            if (!u.getActive()) {
                throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
            }
             u.setPasswordResetToken(tokenProvider.generatePasswordResetToken(u.getUserUniqueId()));
             adminUserRepository.save(u);

             simpleEmailService.sendResetPasswordEmail(
                    new Mail(u.getEmail(), u.getName(), u.getUsername()), u.getPasswordResetToken());
        });

        return true;
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        AdminUser adminUser = adminUserRepository.findAdminUserByEmailVerificationToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        if (!hasTokenExpired) {
            adminUser.setEmailVerificationToken(null);
            adminUser.setActive(Boolean.TRUE);
            adminUserRepository.save(adminUser);
            returnValue = true;
        }
        return returnValue;
    }

    public boolean resetPassword(String token, PasswordReset passwordReset) {
        boolean returnValue = false;
        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        AdminUser adminUser = adminUserRepository.findAdminUserByPasswordResetToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        if (!adminUser.getActive()) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
        }

        if (!passwordReset.getPassword().equals(passwordReset.getConfirmPassword())) {
            throw new UserAuthenticationException(UserMessages.PASSWORDS_EQUALS.getErrorMessage());
        }

        if (!hasTokenExpired) {
                String encodedPassword = passwordEncoder.encode(passwordReset.getPassword());

                adminUser.setPassword(encodedPassword);
                adminUser.setPasswordResetToken(null);
                adminUserRepository.save(adminUser);

                returnValue = true;
        }
        return returnValue;
    }

}