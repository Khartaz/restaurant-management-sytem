package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.RoleName;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.user.*;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountUserService extends UserDetailsService {

    Company getCompany(@CurrentUser UserPrincipal currentUser);

    @Override
    UserDetails loadUserByUsername(String usernameOrEmail);

    UserDetails loadUserByUserId(Long id);

    ApiResponse checkEmailAvailability(String email);

    ApiResponse checkEmailAvailabilityInCompany(@CurrentUser UserPrincipal currentUser, String email);

    AccountUser updateAccountInfo(@CurrentUser UserPrincipal currentUser, AccountUserDTO accountUserDTO);

    AccountUser getUserById(Long id);

    AccountUser getCompanyUserById(@CurrentUser UserPrincipal currentUser, Long id);

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    boolean requestResetPassword(String email);

    boolean resendEmailVerificationToken(String email);

    boolean verifyEmailToken(String token);

    boolean resetPassword(String token, PasswordReset passwordReset);

    String getRoleToString(RoleName roleName);

    ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, NewPasswordRequest request);

}
