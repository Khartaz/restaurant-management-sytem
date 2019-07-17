package com.restaurant.management.repository.layout;

import com.restaurant.management.domain.layout.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayoutSettingsRepository extends JpaRepository<Settings, Long> {
}
