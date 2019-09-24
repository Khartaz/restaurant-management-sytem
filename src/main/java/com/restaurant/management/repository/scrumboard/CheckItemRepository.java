package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.CheckItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckItemRepository extends JpaRepository<CheckItem, Long> {
}
