package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByRestaurantInfoId(Long restaurantId, Pageable pageable);

    Optional<Order> findByIdAndRestaurantInfoId(Long orderId, Long restaurantId);

    Long countAllByRestaurantInfoId(Long id);

    Long countAllByRestaurantInfoIdAndCreatedAtBetween(Long restaurantId, Long startDate, Long endDate);

    Page<Order> findByRestaurantInfoIdAndCreatedAtBetween(Long restaurantId, Long startDate, Long endDate, Pageable pageable);
}
