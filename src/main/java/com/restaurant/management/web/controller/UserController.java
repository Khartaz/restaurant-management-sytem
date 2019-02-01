package com.restaurant.management.web.controller;

import com.restaurant.management.domain.Mail;
import com.restaurant.management.domain.User;
import com.restaurant.management.repository.UserRepository;
import com.restaurant.management.security.JwtTokenProvider;
import com.restaurant.management.service.CustomUserDetailsService;
import com.restaurant.management.service.SimpleEmailService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private CustomUserDetailsService customUserDetailsService;
    private UserRepository userRepository;
    private JwtTokenProvider tokenProvider;
    private SimpleEmailService simpleEmailService;

    @Autowired
    public void setTokenProvider(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Autowired
    public void setSimpleEmailService(SimpleEmailService simpleEmailService) {
        this.simpleEmailService = simpleEmailService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(customUserDetailsService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = customUserDetailsService.createUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(signUpRequest.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value = "/email-verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyEmailToken(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(customUserDetailsService.verifyEmailToken(token));
    }
}