package com.restaurant.management.repository;

import com.restaurant.management.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    Page<Order> findAll(Pageable pageable);

    Optional<Order> findByOrderNumber(String orderNumber);
}
