package com.restaurant.management.repository;

import com.restaurant.management.domain.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUniqueId(String uniqueId);

    @Override
    Page<Cart> findAll(Pageable pageable);

    Page<Cart> findByCustomerId(Long id, Pageable pageable);

    Optional<Cart> findByCustomerId(Long id);

    List<Cart> findByCustomerPhoneNumber(Long phoneNumber);

    Optional<Cart> findByCustomerIdAndUniqueId(Long id, String uniqueId);
}
