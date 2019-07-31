package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.CartOrdered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartOrderedRepository extends JpaRepository<CartOrdered, Long> {

    @Override
    Page<CartOrdered> findAll(Pageable pageable);

    Page<CartOrdered> findAllByCompanyId(Long companyId, Pageable pageable);

    Optional<CartOrdered> findByIdAndCompanyId(Long cartId, Long companyId);

}
