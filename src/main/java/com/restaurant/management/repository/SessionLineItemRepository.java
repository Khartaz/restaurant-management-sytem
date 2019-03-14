package com.restaurant.management.repository;

import com.restaurant.management.domain.SessionLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<SessionLineItem, Long> {

    void deleteById(Long id);
}
