package com.restaurant.management.repository;

import com.restaurant.management.domain.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

    void deleteById(Long id);
}
