package com.restaurant.management.service.ecommerce;

import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;

public interface LayoutSettingsService {

    Settings assignLayoutSettings(Long id);

    Settings createDefaultLayoutSettings();

    Settings updateSettings(@CurrentUser UserPrincipal currentUser, Settings settings);
}
