package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.BoardSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardSettingsRepository extends JpaRepository<BoardSettings, Long> {
}
