package com.restaurant.management.service.ecommerce;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.RoleName;
import com.restaurant.management.domain.ecommerce.dto.UserDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.user.*;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Company getCompany(@CurrentUser UserPrincipal currentUser);

    @Override
    UserDetails loadUserByUsername(String usernameOrEmail);

    UserDetails loadUserByUserId(Long id);

    ApiResponse checkEmailAvailability(String email);

    ApiResponse checkEmailAvailabilityInCompany(@CurrentUser UserPrincipal currentUser, String email);

    User updateAccountInfo(@CurrentUser UserPrincipal currentUser, UserDTO userDTO);

    User getUserById(Long id);

    User getCompanyUserById(@CurrentUser UserPrincipal currentUser, Long id);

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    boolean requestResetPassword(String email);

    boolean resendEmailVerificationToken(String email);

    boolean verifyEmailToken(String token);

    boolean resetPassword(String token, PasswordReset passwordReset);

    String getRoleToString(RoleName roleName);

    ApiResponse newPasswordRequest(@CurrentUser UserPrincipal userPrincipal, NewPasswordRequest request);

}
