package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.ProductOrdered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderedRepository extends JpaRepository<ProductOrdered, Long> {
}
