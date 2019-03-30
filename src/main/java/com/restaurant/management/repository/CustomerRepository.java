package com.restaurant.management.repository;

import com.restaurant.management.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPhoneNumber(Long phoneNumber);

    Boolean existsByPhoneNumber(Long phoneNumber);

    Boolean existsByEmail(String email);

    Optional<Customer> findByPhoneNumberEndingWith(Long phoneNumber);

    Optional<Customer> findById(Long id);

    void deleteById(Long id);

    @Override
    Page<Customer> findAll(Pageable pageable);
}
