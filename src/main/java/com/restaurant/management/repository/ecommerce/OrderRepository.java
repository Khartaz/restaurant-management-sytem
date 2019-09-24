package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.Company;
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

    Page<Order> findAllByCompany(Company company, Pageable pageable);

    Optional<Order> findByIdAndCompanyId(Long orderId, Long companyId);

    Long countAllByCompanyId(Long id);

    Page<Order> findByCompanyIdAndCreatedAtBetween(Long companyId, Long startDate, Long endDate, Pageable pageable);
}
