package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.layout.Shortcut;
import com.restaurant.management.exception.NotFoundException;
import com.restaurant.management.repository.layout.LayoutShortcutRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutShortcutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("Duplicates")
public class LayoutShortcutServiceImpl implements LayoutShortcutService {
    private AccountUserService accountUserService;
    private LayoutShortcutRepository shortcutRepository;

    @Autowired
    public LayoutShortcutServiceImpl(AccountUserService accountUserService,
                                     LayoutShortcutRepository shortcutRepository) {
        this.accountUserService = accountUserService;
        this.shortcutRepository = shortcutRepository;
    }

    public Shortcut createDefaultLayoutShortcut() {
        return new Shortcut("0");
    }

    public Shortcut assignDefaultShortcut(Long id) {
        AccountUser accountUser = accountUserService.getUserById(id);

        Shortcut shortcut = createDefaultLayoutShortcut();

        shortcut.setAccountUser(accountUser);

        shortcutRepository.save(shortcut);

        return shortcut;
    }

    public List<String> addLayoutShortcut(@CurrentUser UserPrincipal currentUser, List<Shortcut> shortcuts) {
        AccountUser accountUser = accountUserService.getUserById(currentUser.getId());

        Shortcut result = shortcutRepository.findByAccountUserId(accountUser.getId())
                .orElse(new Shortcut());

        if (!shortcuts.isEmpty()) {
            Set<String> shortcutsNames = shortcuts.stream()
                    .map(Shortcut::getShortcut)
                    .collect(Collectors.toSet());

            String shortcutsAsString = String.join(", ", shortcutsNames);

            result.setShortcut(shortcutsAsString);
            result.setAccountUser(accountUser);
        }

        if (shortcuts.isEmpty()) {
            result.setShortcut("0");
            result.setAccountUser(accountUser);
        }

        shortcutRepository.save(result);

        return new ArrayList<>();
    }

    public String[] getLayoutShortcuts(@CurrentUser UserPrincipal currentUser) {
        Shortcut result = shortcutRepository.findByAccountUserId(currentUser.getId())
                .orElseThrow(() -> new NotFoundException("Shortcut not found"));

        String shortcutName = result.getShortcut();

        return shortcutName.split(", ");
    }

    public String[] getLayoutShortcutsFromAccountId(Long id) {
        Shortcut result = shortcutRepository.findByAccountUserId(id)
                .orElseThrow(() -> new NotFoundException("Shortcut not found"));

        String shortcutName = result.getShortcut();

        return shortcutName.split(", ");
    }
}
