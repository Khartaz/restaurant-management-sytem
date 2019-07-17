package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhoneNumberAndRestaurantInfoId(String phoneNumber, Long restaurantId);

    Boolean existsByEmailAndRestaurantInfoId(String email, Long restaurantId);

    Boolean existsByIdAndRestaurantInfoId(Long id, Long restaurantId);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByIdAndRestaurantInfoId(Long id, Long restaurantId);

    void deleteById(Long id);

    Page<Customer> findAllByRestaurantInfoId(Pageable pageable, Long restaurantId);

    Page<Customer> findAllByNameIsContainingAndRestaurantInfoId(String name, Long restaurantId, Pageable pageable);

    Page<Customer> findAllByPhoneNumberIsContainingAndRestaurantInfoId(String phoneNumber, Long restaurantId, Pageable pageable);

    Page<Customer> findAllByLastnameContainingAndRestaurantInfoId(String lastname, Long restaurantId, Pageable pageable);

}
