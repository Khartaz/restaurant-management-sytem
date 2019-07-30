package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Override
    Page<Cart> findAll(Pageable pageable);

    Page<Cart> findAllByCompanyId(Long companyId, Pageable pageable);

    Optional<Cart> findByIdAndCompanyId(Long cartId, Long companyId);

}
