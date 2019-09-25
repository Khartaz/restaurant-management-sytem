package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.domain.layout.settings.Layout;
import com.restaurant.management.domain.layout.settings.layout.Config;
import com.restaurant.management.domain.layout.settings.layout.config.Footer;
import com.restaurant.management.domain.layout.settings.layout.config.Navbar;
import com.restaurant.management.domain.layout.settings.layout.config.Toolbar;
import com.restaurant.management.domain.layout.settings.theme.Theme;
import com.restaurant.management.exception.NotFoundException;
import com.restaurant.management.repository.ecommerce.UserRepository;
import com.restaurant.management.repository.layout.LayoutSettingsRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.UserService;
import com.restaurant.management.service.ecommerce.LayoutSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LayoutSettingsServiceImpl implements LayoutSettingsService {
    private UserService userService;
    private UserRepository userRepository;
    private LayoutSettingsRepository settingsRepository;

    @Autowired
    public LayoutSettingsServiceImpl(UserService userService,
                                     UserRepository userRepository,
                                     LayoutSettingsRepository settingsRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
        User user = userService.getUserById(id);

        Settings settings = createDefaultLayoutSettings();

        user.setSettings(createDefaultLayoutSettings());

        userRepository.save(user);

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
