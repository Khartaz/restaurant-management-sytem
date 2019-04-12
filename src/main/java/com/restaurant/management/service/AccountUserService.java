package com.restaurant.management.service;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.Mail;
import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.request.UpdateAccountNameOrLastname;
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
import java.util.List;
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
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserService(AuthenticationManager authenticationManager, AccountUserRepository userRepository,
                              RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                              JwtTokenProvider tokenProvider, SimpleEmailService simpleEmailService,
                              AccountUserMapper accountUserMapper) {
        this.authenticationManager = authenticationManager;
        this.accountUserRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.simpleEmailService = simpleEmailService;
        this.accountUserMapper = accountUserMapper;
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

    public AccountUserDto registerAdminAccount(SignUpUserRequest signUpUserRequest) {
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

        return accountUserMapper.mapToAccountUserDto(newAdminUser);
    }

    public AccountUserDto registerManagerAccount(SignUpUserRequest signUpUserRequest) {
        if(accountUserRepository.existsByEmail(signUpUserRequest.getEmail())) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getErrorMessage());
        }

        String userUniqueId = Utils.generateUserUniqueId(10);

        String token = tokenProvider.generateEmailVerificationToken(userUniqueId);

        Role userRole = roleRepository.findByName(RoleName.MANAGER)
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

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public boolean deleteUserById(Long id) {
        Optional<AccountUser> accountUser = accountUserRepository.findById(id);

        if (accountUser.isPresent()) {
            accountUserRepository.deleteById(accountUser.get().getId());
            return true;
        } else {
            throw new UserNotFoundException(UserMessages.ID_NOT_FOUND.getErrorMessage() + id);
        }
    }

    public AccountUserDto updateAccountNameOrLastname(UpdateAccountNameOrLastname request) {
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + request.getUsernameOrEmail()));

        Stream.of(accountUser).forEach(acc -> {
            acc.setName(request.getName());
            acc.setLastname(request.getLastname());
        });

        accountUserRepository.save(accountUser);

        return accountUserMapper.mapToAccountUserDto(accountUser);
    }

    public AccountUserDto getUserByUserUniqueId(String userUniqueId) {
        Optional<AccountUser> accountUser = accountUserRepository.findByUserUniqueId(userUniqueId);

        if (!accountUser.isPresent()) {
            throw new UserNotFoundException(UserMessages.UNIQUE_ID_NOT_FOUND.getErrorMessage());
        }

        return accountUserMapper.mapToAccountUserDto(accountUser.get());
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        AccountUser accountUser = accountUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getErrorMessage() + usernameOrEmail));

        if (!accountUser.isActive()) {
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

    public List<AccountUserDto> getAllAccountUsers() {
        List<AccountUser> accountUsers = accountUserRepository.findAll();

        return accountUserMapper.mapToAccountUserListDto(accountUsers);
    }

}