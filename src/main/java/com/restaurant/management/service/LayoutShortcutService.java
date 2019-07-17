package com.restaurant.management.service;

import com.restaurant.management.domain.layout.Shortcut;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;

import java.util.List;

public interface LayoutShortcutService {

    Shortcut createDefaultLayoutShortcut();

    Shortcut assignDefaultShortcut(Long id);

    List<String> addLayoutShortcut(@CurrentUser UserPrincipal currentUser, List<Shortcut> shortcuts);

    String[] getLayoutShortcuts(@CurrentUser UserPrincipal currentUser);

    String[] getLayoutShortcutsFromAccountId(Long id);

}
