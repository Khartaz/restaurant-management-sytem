package com.restaurant.management.audit;

import com.restaurant.management.security.UserPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditingAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {

            return Optional.empty();

        } else if (!authentication.isAuthenticated() || authentication.getName() != null) {

            return Optional.ofNullable(authentication.getName());

        } else {

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            return Optional.ofNullable(userPrincipal.getUsername());
        }

    }
}