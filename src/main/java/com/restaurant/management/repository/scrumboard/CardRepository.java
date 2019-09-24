package com.restaurant.management.repository.scrumboard;

import com.restaurant.management.domain.scrumboard.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
