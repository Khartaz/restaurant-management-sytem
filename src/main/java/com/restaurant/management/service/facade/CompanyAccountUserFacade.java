package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.layout.Shortcut;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.LayoutShortcutService;
import com.restaurant.management.service.CompanyService;
import com.restaurant.management.web.request.company.RegisterCompanyRequest;
import com.restaurant.management.web.request.user.UserUpdateRequest;
import com.restaurant.management.web.response.company.RegisterCompany;
import com.restaurant.management.web.response.user.UserDetailsResponse;
import com.restaurant.management.web.response.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.restaurant.management.mapper.RoleMapper.roleToString;

@Component
@SuppressWarnings("Duplicates")
public final class CompanyAccountUserFacade {

    private CompanyService companyService;
    private AccountUserService accountUserService;
    private JwtTokenProvider jwtTokenProvider;
    private LayoutSettingsService layoutSettingsService;
    private LayoutShortcutService shortcutService;

    @Autowired
    public CompanyAccountUserFacade(CompanyService companyService,
                                    AccountUserService accountUserService,
                                    JwtTokenProvider jwtTokenProvider,
                                    LayoutSettingsService layoutSettingsService,
                                    LayoutShortcutService shortcutService) {
        this.companyService = companyService;
        this.accountUserService = accountUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.layoutSettingsService = layoutSettingsService;
        this.shortcutService = shortcutService;
    }

    public UserResponse registerCompany(RegisterCompanyRequest request) {
        String photoURL = "assets/images/avatars/profile.jpg";

        RegisterCompany registeredCompany = companyService.registerCompany(request);

        AccountUser user = registeredCompany.getAccountUser();

        shortcutService.assignDefaultShortcut(user.getId());

        String token = jwtTokenProvider.generateRegistrationToken(user.getId());

        String[] shortcuts = shortcutService.getLayoutShortcutsFromAccountId(user.getId());

        AccountUser accountUser = registeredCompany.getAccountUser();

        UserDetailsResponse userDetails = new UserDetailsResponse(
                accountUser.getCreatedAt(),
                accountUser.getUpdatedAt(),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                roleToString(accountUser),
                accountUser.getName(),
                accountUser.getLastName(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhone()
        );

        return new UserResponse(userDetails, accountUser.getSettings(), shortcuts, token);
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
                accountUser.getLastName(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhone()
        );

        return new UserResponse(userDetailsResponse, accountUser.getSettings(), shortcuts);
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
                accountUser.getLastName(),
                photoURL,
                accountUser.getEmail(),
                accountUser.getPhone()
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
