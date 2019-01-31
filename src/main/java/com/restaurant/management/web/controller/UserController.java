package com.restaurant.management.web.controller;

import com.restaurant.management.repository.UserRepository;
import com.restaurant.management.service.CustomUserDetailsService;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;

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
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        customUserDetailsService.createUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(signUpRequest.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value = "/email-verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public String verifyEmailToken(@RequestParam(value = "token") String token) {
        String result;
        boolean isVerified = customUserDetailsService.verifyEmailToken(token);

        if (isVerified) {
            result = "Email verified. Account is enabled";
        } else {
            result = "Error";
        }
        return result;
    }
}