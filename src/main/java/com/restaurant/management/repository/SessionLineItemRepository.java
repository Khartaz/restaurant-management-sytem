package com.restaurant.management.repository;

import com.restaurant.management.domain.SessionLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionLineItemRepository extends JpaRepository<SessionLineItem, Long> {

    void deleteById(Long id);

    List<SessionLineItem> findAllByProductUniqueId(String uniqueId);

}
