package com.restaurant.management.web.controller;

import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.web.request.PasswordReset;
import com.restaurant.management.web.request.PasswordResetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthController {

    private AccountUserService accountUserService;

    @Autowired
    public void setAccountUserService(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    @GetMapping(value = "/auth/email-verification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyEmailToken(@RequestParam(value = "token") String token) {
        return ResponseEntity.ok(accountUserService.verifyEmailToken(token));
    }

    @PostMapping(value = "/auth/password-reset-request",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPasswordRequest(@RequestBody PasswordResetRequest passwordResetRequest) {

        return ResponseEntity.ok(accountUserService.requestResetPassword(passwordResetRequest.getUsernameOrEmail()));
    }

    @PostMapping(value = "/auth/reset-password",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@RequestParam(value = "token") String token,
                                           @RequestBody PasswordReset passwordReset) {

        return ResponseEntity.ok(accountUserService.resetPassword(token, passwordReset));
    }

    @PostMapping(value = "/auth/email-token", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendEmailToken(@RequestParam String usernameOrEmail) {
        return ResponseEntity.ok(accountUserService.resendEmailVerificationToken(usernameOrEmail));
    }

}
