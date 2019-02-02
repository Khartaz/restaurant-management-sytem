package com.restaurant.management.web.controller;

import com.restaurant.management.service.CustomUserDetailsService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.PasswordResetRequest;
import com.restaurant.management.web.request.SignUpRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.request.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AccountController {

    private CustomUserDetailsService customUserDetailsService;

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
        customUserDetailsService.createUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(signUpRequest.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value = "/email-verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyEmailToken(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(customUserDetailsService.verifyEmailToken(token));
    }

    @PostMapping(value = "/password-reset-request",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest) {

        return ResponseEntity.ok(customUserDetailsService.requestResetPassword(passwordResetRequest.getUsernameOrEmail()));
    }

    @PostMapping(value = "/reset-password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@RequestParam(value = "token") String token,
                                           @RequestBody PasswordReset passwordReset) {

        return ResponseEntity.ok(customUserDetailsService.resetPassword(token, passwordReset));
    }
}