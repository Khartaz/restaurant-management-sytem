package com.restaurant.management.service;


import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.RestaurantInfo;
import com.restaurant.management.domain.RoleName;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.account.LoginRequest;
import com.restaurant.management.web.request.account.PasswordReset;
import com.restaurant.management.web.request.account.SignUpUserRequest;
import com.restaurant.management.web.request.account.UpdateAccountInfo;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.JwtAuthenticationResponse;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountUserService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String usernameOrEmail);

    UserDetails loadUserByUserId(Long id);

    ApiResponse checkEmailAvailability(String email);

    AccountUser registerAdminAccount(SignUpUserRequest signUpUserRequest);

    AccountUser registerManagerAccount(SignUpUserRequest signUpUserRequest);

    ApiResponse deleteUserById(Long id);

    AccountUser updateAccountInfo(@CurrentUser UserPrincipal currentUser, UpdateAccountInfo request);

    AccountUser getUserById(Long id);

    AccountUser getRestaurantUserById(@CurrentUser UserPrincipal currentUser, Long id);

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest);

    boolean requestResetPassword(String email);

    boolean resendEmailVerificationToken(String email);

    boolean verifyEmailToken(String token);

    boolean resetPassword(String token, PasswordReset passwordReset);

    Page<AccountUser> getAllAccountUsers(Pageable pageable);

    Page<AccountUser> getRestaurantUsers(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    RoleName[] getRoles();

}
