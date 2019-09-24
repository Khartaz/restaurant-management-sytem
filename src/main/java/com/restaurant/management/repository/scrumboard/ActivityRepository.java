package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
