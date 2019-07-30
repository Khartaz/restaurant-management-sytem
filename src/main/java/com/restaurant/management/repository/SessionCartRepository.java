package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.SessionCart;
import com.restaurant.management.domain.ecommerce.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionCartRepository extends JpaRepository<SessionCart, Long> {

    Optional<SessionCart> findSessionCartByCustomerIdAndIsOpenTrue(Long id);

    Optional<SessionCart> findByCustomer(Customer customer);

    Optional<SessionCart> findSessionCartByCustomerIdAndCompanyId(Long customerId, Long companyId);

    Optional<SessionCart> findByIdAndCompanyId(Long cartId, Long companyId);

    boolean existsByCustomerIdAndIsOpenTrue(Long id);

    @Override
    Page<SessionCart> findAll(Pageable pageable);

    Page<SessionCart> findAllByCompanyId(Long companyId, Pageable pageable);
}

