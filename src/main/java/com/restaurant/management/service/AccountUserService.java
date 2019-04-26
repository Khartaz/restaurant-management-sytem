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
import com.restaurant.management.web.request.UpdateAccountNameOrLastname;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.request.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;
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
    private SimpleEmailService simpleEmailService;

    @Autowired
    public AccountUserService(AuthenticationManager authenticationManager, AccountUserRepository userRepository,
                              RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                              JwtTokenProvider tokenProvider, SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.accountUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
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
        if(accountUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }

        String userUniqueId = Utils.generateUserUniqueId(10);

        String token = tokenProvider.generateEmailVerificationToken(userUniqueId);

        Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getErrorMessage()));

        AccountUser newAdminUser = new AccountUser.AccountUserBuilder()
                .setName(signUpUserRequest.getName())
                .setLastname(signUpUserRequest.getLastname())
                .setEmail(signUpUserRequest.getEmail())
                .setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()))
                .setIsActive(Boolean.FALSE)
                .setUserUniqueId(userUniqueId)
                .setRoles(Collections.singleton(userRole))
                .setEmailVerificationToken(token)
                .build();

        accountUserRepository.save(newAdminUser);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName()), token);

        return newAdminUser;
    }

    public AccountUser registerManagerAccount(SignUpUserRequest signUpUserRequest) {
        if(accountUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }

        String userUniqueId = Utils.generateUserUniqueId(10);

        String token = tokenProvider.generateEmailVerificationToken(userUniqueId);

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getErrorMessage()));

        AccountUser accountUser = new AccountUser.AccountUserBuilder()
                .setName(signUpUserRequest.getName())
                .setLastname(signUpUserRequest.getLastname())
                .setEmail(signUpUserRequest.getEmail())
                .setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()))
                .setIsActive(Boolean.FALSE)
                .setUserUniqueId(userUniqueId)
                .setRoles(Collections.singleton(userRole))
                .setEmailVerificationToken(token)
                .build();

        accountUserRepository.save(accountUser);

        simpleEmailService.sendEmailVerification(
                new Mail(signUpUserRequest.getEmail(), signUpUserRequest.getName()), token);

        return accountUser;
    }

    public ApiResponse deleteUserById(Long id) {
        AccountUser accountUser = accountUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getErrorMessage() + id));

        accountUserRepository.deleteById(accountUser.getId());

        return new ApiResponse(true, UserMessages.ACCOUNT_DELETED.getErrorMessage());
    }

    public AccountUser updateAccountNameOrLastname(UpdateAccountNameOrLastname request) {
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(request.getEmail(), request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + request.getEmail()));

        Stream.of(accountUser).forEach(acc -> {
            acc.setName(request.getName());
            acc.setLastname(request.getLastname());
        });

        accountUserRepository.save(accountUser);

        return accountUser;
    }

    public AccountUser getUserByUserUniqueId(String userUniqueId) {
        Optional<AccountUser> accountUser = accountUserRepository.findByUserUniqueId(userUniqueId);

        if (!accountUser.isPresent()) {
            throw new UserNotFoundException(UserMessages.UNIQUE_ID_NOT_FOUND.getErrorMessage() + userUniqueId);
        }

        return accountUser.get();
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getEmail();
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        if (!accountUser.isActive()) {
            throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean requestResetPassword(String usernameOrEmail) {
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        Stream.of(accountUser).forEach(u -> {
            if (!u.isActive()) {
                throw new UserAuthenticationException(UserMessages.ACCOUNT_DISABLED.getErrorMessage());
            }
             u.setPasswordResetToken(tokenProvider.generatePasswordResetToken(u.getUserUniqueId()));

             accountUserRepository.save(u);

             simpleEmailService.sendResetPasswordEmail(
                    new Mail(u.getEmail(), u.getName()), u.getPasswordResetToken());
        });

        return true;
    }

    public boolean resendEmailVerificationToken(String usernameOrEmail) {
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        String token = accountUser.getEmailVerificationToken();

        simpleEmailService.sendEmailVerification(
                new Mail(accountUser.getEmail(), accountUser.getName()), token);

        return true;
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        AccountUser accountUser = accountUserRepository.findAdminUserByEmailVerificationToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        String username = Utils.generateUsername(accountUser.getName(), accountUser.getLastname(), accountUser.getId());

        if (!hasTokenExpired) {
            accountUser.setEmailVerificationToken(null);
            accountUser.setActive(Boolean.TRUE);
            accountUser.setUsername(username);

            accountUserRepository.save(accountUser);

            returnValue = true;
        }
        return returnValue;
    }

    public boolean resetPassword(String token, PasswordReset passwordReset) {
        boolean returnValue = false;
        boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);

        AccountUser accountUser = accountUserRepository.findAdminUserByPasswordResetToken(token)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.UNAUTHENTICATED.getErrorMessage()));

        if (!accountUser.isActive()) {
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

    public Page<AccountUser> getAllAccountUsers(Pageable pageable) {
        return accountUserRepository.findAll(pageable);
    }

}