package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}
