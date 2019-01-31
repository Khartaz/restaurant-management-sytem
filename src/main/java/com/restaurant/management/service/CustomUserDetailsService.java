package com.restaurant.management.service;

import com.restaurant.management.domain.Mail;
import com.restaurant.management.domain.Role;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.domain.User;
import com.restaurant.management.exception.AppException;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.repository.UserRepository;
import com.restaurant.management.security.JwtTokenProvider;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpRequest;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private Utils utils;
    private SimpleEmailService simpleEmailService;


    @Autowired
    public CustomUserDetailsService(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider, Utils utils, SimpleEmailService simpleEmailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.utils = utils;
        this.simpleEmailService = simpleEmailService;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );
        return UserPrincipal.create(user);
    }

    public User createUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        String userId = utils.generatePublicId(15);
        String token = tokenProvider.generateEmailVerificationToken(userId);

        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailVerificationToken(token);
        user.setEnabled(false);

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        simpleEmailService.sendEmailVerification(
                new Mail(signUpRequest.getEmail(), signUpRequest.getName(), signUpRequest.getUsername()), token);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {
        String usernameOrEmail = loginRequest.getUsernameOrEmail();
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

        if (!user.getEnabled()) {
            throw new UsernameNotFoundException("Account is disabled. Please verify email first.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return new JwtAuthenticationResponse(jwt);
    }

    public boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        User user = userRepository.findUserByEmailVerificationToken(token);

        if (user != null) {
            boolean hasTokenExpired = new JwtTokenProvider().hasTokenExpired(token);
            if (!hasTokenExpired) {
                user.setEmailVerificationToken(null);
                user.setEnabled(Boolean.TRUE);
                userRepository.save(user);
                returnValue = true;
            }
        }
        return returnValue;
    }
}