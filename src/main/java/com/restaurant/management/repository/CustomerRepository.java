package com.restaurant.management.repository;

import com.restaurant.management.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhoneNumber(Long phoneNumber);

    Boolean existsByEmail(String email);

    Boolean existsByIdAndRestaurantInfoId(Long id, Long restaurantId);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByIdAndRestaurantInfoId(Long id, Long restaurantId);

    void deleteById(Long id);

    @Override
    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findAllByRestaurantInfoId(Pageable pageable, Long restaurantId);
}
