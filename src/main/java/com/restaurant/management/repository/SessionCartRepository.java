package com.restaurant.management.repository;

import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionCartRepository extends JpaRepository<SessionCart, Long> {

    Optional<SessionCart> findSessionCartByCustomerIdAndIsOpenTrue(Long id);

    Optional<SessionCart> findByCustomer(Customer customer);

    Optional<SessionCart> findSessionCartByCustomerId(Long id);

    Optional<SessionCart> findSessionCartByCustomerIdAndRestaurantInfoId(Long customerId, Long restaurantId);

    Optional<SessionCart> findByIdAndRestaurantInfoId(Long cartId, Long restaurantId);

    boolean existsByCustomerAndIsOpenTrue(Customer customer);

    @Override
    Page<SessionCart> findAll(Pageable pageable);

    Page<SessionCart> findAllByRestaurantInfoId(Long restaurantId, Pageable pageable);
}

