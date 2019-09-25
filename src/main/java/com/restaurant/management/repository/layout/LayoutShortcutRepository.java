package com.restaurant.management.repository.layout;

import com.restaurant.management.domain.layout.Shortcut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LayoutShortcutRepository extends JpaRepository<Shortcut, Long> {

    Optional<Shortcut> findByUserId(Long id);
}
