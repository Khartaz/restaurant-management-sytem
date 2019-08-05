package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.domain.ecommerce.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByCustomerIdAndIsOpenTrue(Long id);

    List<Cart> findAllByCustomer(Customer customer);

    Optional<Cart> findCartByCustomerIdAndCompanyId(Long customerId, Long companyId);

    Optional<Cart> findByIdAndCompanyId(Long cartId, Long companyId);

    boolean existsByCustomerIdAndIsOpenTrue(Long id);

    @Override
    Page<Cart> findAll(Pageable pageable);

    Page<Cart> findAllByCompanyId(Long companyId, Pageable pageable);
}

