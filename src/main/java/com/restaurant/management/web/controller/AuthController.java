package com.restaurant.management.web.controller;

import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.user.PasswordReset;
import com.restaurant.management.web.request.user.PasswordResetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AccountUserService accountUserService;

    @Autowired
    public AuthController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    @GetMapping(value = "/email-verification", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyEmailToken(@RequestParam String token) {
        return ResponseEntity.ok(accountUserService.verifyEmailToken(token));
    }

    @PostMapping(value = "/password-reset-request",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest) {

        return ResponseEntity.ok(accountUserService.requestResetPassword(passwordResetRequest.getEmail()));
    }

    @PostMapping(value = "/reset-password",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@RequestParam String token,
                                           @RequestBody PasswordReset passwordReset) {
        return ResponseEntity.ok(accountUserService.resetPassword(token, passwordReset));
    }

    @PostMapping(value = "/email-token",
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailToken(@RequestParam String email) {
        return ResponseEntity.ok(accountUserService.resendEmailVerificationToken(email));
    }

}
