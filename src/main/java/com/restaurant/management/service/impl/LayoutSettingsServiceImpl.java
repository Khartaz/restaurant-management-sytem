package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.domain.layout.settings.Layout;
import com.restaurant.management.domain.layout.settings.layout.Config;
import com.restaurant.management.domain.layout.settings.layout.config.Footer;
import com.restaurant.management.domain.layout.settings.layout.config.Navbar;
import com.restaurant.management.domain.layout.settings.layout.config.Toolbar;
import com.restaurant.management.domain.layout.settings.theme.Theme;
import com.restaurant.management.exception.NotFoundException;
import com.restaurant.management.repository.ecommerce.AccountUserRepository;
import com.restaurant.management.repository.layout.LayoutSettingsRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LayoutSettingsServiceImpl implements LayoutSettingsService {
    private AccountUserService accountUserService;
    private AccountUserRepository accountUserRepository;
    private LayoutSettingsRepository settingsRepository;

    @Autowired
    public LayoutSettingsServiceImpl(AccountUserService accountUserService,
                                     AccountUserRepository accountUserRepository,
                                     LayoutSettingsRepository settingsRepository) {
        this.accountUserService = accountUserService;
        this.accountUserRepository = accountUserRepository;
        this.settingsRepository =settingsRepository;
    }

    public Settings createDefaultLayoutSettings() {
        return new Settings(
                "assets/images/avatars/profile.jpg",

                Boolean.TRUE,

                new Layout(
                        "layout1",
                        new Config(
                                "content",
                                "fullwidth",
                                new Navbar(
                                        Boolean.TRUE,
                                        Boolean.TRUE,
                                        "left"
                                ),
                                new Toolbar(
                                        Boolean.TRUE,
                                        "fixed",
                                        "below"
                                ),
                                new Footer(
                                        Boolean.TRUE,
                                        "fixed",
                                        "below"
                                )
                        )
                ),
                new Theme(
                        "defaultDark",
                        "defaultDark",
                        "defaultDark",
                        "defaultDark"
                )
        );
    }

    public Settings assignLayoutSettings(Long id) {
        AccountUser accountUser = accountUserService.getUserById(id);

        Settings settings = createDefaultLayoutSettings();

        accountUser.setSettings(createDefaultLayoutSettings());

        accountUserRepository.save(accountUser);

        return settings;
    }

    public Settings updateSettings(@CurrentUser UserPrincipal currentUser, Settings settings) {
        Settings result = settingsRepository.findById(settings.getId())
                .orElseThrow(() -> new NotFoundException("Settings not found."));

        result.setCustomScrollbars(settings.getCustomScrollbars());
        result.setLayout(settings.getLayout());
        result.setTheme(settings.getTheme());

        settingsRepository.save(result);

        return result;
    }

}
