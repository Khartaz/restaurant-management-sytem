package com.restaurant.management.service.facade;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.ecommerce.RestaurantInfo;
import com.restaurant.management.domain.layout.Shortcut;
import com.restaurant.management.mapper.RestaurantInfoMapper;
import com.restaurant.management.mapper.RoleMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.LayoutShortcutService;
import com.restaurant.management.service.RestaurantInfoService;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.request.user.UserUpdateRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurant;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;
import com.restaurant.management.web.response.user.UserDetailsResponse;
import com.restaurant.management.web.response.user.UserResponse;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("Duplicates")
public final class RestaurantInfoAccountUserFacade {

    private RestaurantInfoService restaurantInfoService;
    private AccountUserService accountUserService;
    private RestaurantInfoMapper restaurantInfoMapper;
    private JwtTokenProvider jwtTokenProvider;
    private LayoutSettingsService layoutSettingsService;
    private LayoutShortcutService shortcutService;

    @Autowired
    public RestaurantInfoAccountUserFacade(RestaurantInfoService restaurantInfoService,
                                           AccountUserService accountUserService,
                                           RestaurantInfoMapper restaurantInfoMapper,
                                           JwtTokenProvider jwtTokenProvider,
                                           LayoutSettingsService layoutSettingsService,
                                           LayoutShortcutService shortcutService) {
        this.restaurantInfoService = restaurantInfoService;
        this.accountUserService = accountUserService;
        this.restaurantInfoMapper = restaurantInfoMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.layoutSettingsService = layoutSettingsService;
        this.shortcutService = shortcutService;
    }

    public UserResponse registerRestaurant(RegisterRestaurantRequest request) {
        String photoURL = "assets/images/avatars/profile.jpg";

        RegisterRestaurant registerRestaurant = restaurantInfoService.registerRestaurant(request);

        AccountUser user = registerRestaurant.getAccountUser();

        shortcutService.assignDefaultShortcut(user.getId());

        String token = jwtTokenProvider.generateRegistrationToken(user.getId());

        String[] shortcuts = shortcutService.getLayoutShortcutsFromAccountId(user.getId());

        AccountUser accountUser = registerRestaurant.getAccountUser();
        UserDetailsResponse userDetails = new UserDetailsResponse(
                accountUser.getCreatedAt(),
                accountUser.getUpdatedAt(),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                roleToString(accountUser),
                accountUser.getName(),
                accountUser.getLastname(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhoneNumber()
        );

        return new UserResponse(userDetails, accountUser.getSettings(), shortcuts, token);
    }

    //TO DELETE IN FUTURE ?
    public UserSummary getUserSummary(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserService.getUserById(currentUser.getId());

        RestaurantInfo restaurantInfo = accountUser.getRestaurantInfo();

        RestaurantInfoResponse restaurantInfoResponse = restaurantInfoMapper.mapToRestaurantInfoResponse(restaurantInfo);

        return new UserSummary(
                accountUser.getId(),
                accountUser.getName(),
                accountUser.getLastname(),
                accountUser.getEmail(),
                accountUser.getPhoneNumber(),
                accountUser.getRoles(),
                restaurantInfoResponse
        );
    }

    public UserResponse getUserData(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserService.getUserById(currentUser.getId());

        String photoURL = "assets/images/avatars/profile.jpg";

        String[] shortcuts = shortcutService.getLayoutShortcuts(currentUser);

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(
                accountUser.getCreatedAt(),
                accountUser.getUpdatedAt(),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                roleToString(accountUser),
                accountUser.getName(),
                accountUser.getLastname(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhoneNumber()
        );

        return new UserResponse(userDetailsResponse, accountUser.getSettings(), shortcuts);
    }

    private String roleToString(AccountUser accountUser) {
        return accountUser.getRoles().stream()
                .map(r -> RoleMapper.mapRoleToString(r.getName()))
                .collect(Collectors.joining());
    }

    public UserResponse getUserDataFromJWT(String token) {
        Long id = jwtTokenProvider.getUserIdFromJWT(token);

        AccountUser accountUser = accountUserService.getUserById(id);

        String photoURL = "assets/images/avatars/profile.jpg";

        String[] shortcuts = shortcutService.getLayoutShortcutsFromAccountId(accountUser.getId());

        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(
                accountUser.getCreatedAt(),
                accountUser.getUpdatedAt(),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                roleToString(accountUser),
                accountUser.getName(),
                accountUser.getLastname(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhoneNumber()
        );

        return new UserResponse(userDetailsResponse, accountUser.getSettings(), shortcuts, token);
    }

    public UserResponse updateUserDetails(@CurrentUser UserPrincipal currentUser, UserUpdateRequest request) {
        layoutSettingsService.updateSettings(currentUser, request.getSettings());

        accountUserService.updateUserDetails(currentUser, request);

        List<Shortcut> shortcuts = request.getShortcuts();

        shortcutService.addLayoutShortcut(currentUser, shortcuts);

        return getUserData(currentUser);
    }

}
