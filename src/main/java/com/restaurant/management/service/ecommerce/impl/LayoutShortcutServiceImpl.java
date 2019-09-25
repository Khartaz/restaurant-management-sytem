package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.layout.Shortcut;
import com.restaurant.management.exception.NotFoundException;
import com.restaurant.management.repository.layout.LayoutShortcutRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.UserService;
import com.restaurant.management.service.ecommerce.LayoutShortcutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("Duplicates")
public class LayoutShortcutServiceImpl implements LayoutShortcutService {
    private UserService userService;
    private LayoutShortcutRepository shortcutRepository;

    @Autowired
    public LayoutShortcutServiceImpl(UserService userService,
                                     LayoutShortcutRepository shortcutRepository) {
        this.userService = userService;
        this.shortcutRepository = shortcutRepository;
    }

    public Shortcut createDefaultLayoutShortcut() {
        return new Shortcut("0");
    }

    public Shortcut assignDefaultShortcut(Long id) {
        User user = userService.getUserById(id);

        Shortcut shortcut = createDefaultLayoutShortcut();

        shortcut.setUser(user);

        shortcutRepository.save(shortcut);

        return shortcut;
    }

    public List<String> addLayoutShortcut(@CurrentUser UserPrincipal currentUser, List<Shortcut> shortcuts) {
        User user = userService.getUserById(currentUser.getId());

        Shortcut result = shortcutRepository.findByUserId(user.getId())
                .orElse(new Shortcut());

        if (!shortcuts.isEmpty()) {
            Set<String> shortcutsNames = shortcuts.stream()
                    .map(Shortcut::getShortcut)
                    .collect(Collectors.toSet());

            String shortcutsAsString = String.join(", ", shortcutsNames);

            result.setShortcut(shortcutsAsString);
            result.setUser(user);
        }

        if (shortcuts.isEmpty()) {
            result.setShortcut("0");
            result.setUser(user);
        }

        shortcutRepository.save(result);

        return new ArrayList<>();
    }

    public String[] getLayoutShortcuts(@CurrentUser UserPrincipal currentUser) {
        Shortcut result = shortcutRepository.findByUserId(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Shortcut not found"));

        String shortcutName = result.getShortcut();

        return shortcutName.split(", ");
    }

    public String[] getLayoutShortcutsFromAccountId(Long id) {
        Shortcut result = shortcutRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Shortcut not found"));

        String shortcutName = result.getShortcut();

        return shortcutName.split(", ");
    }
}
