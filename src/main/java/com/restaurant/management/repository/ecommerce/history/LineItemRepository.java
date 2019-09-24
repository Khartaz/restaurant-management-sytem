package com.restaurant.management.repository.ecommerce.history;

import com.restaurant.management.domain.ecommerce.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

    void deleteById(Long id);

    List<LineItem> findAllByProductId(Long id);
}
