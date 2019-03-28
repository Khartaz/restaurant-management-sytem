package com.restaurant.management.repository;

import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionCartRepository extends JpaRepository<SessionCart, Long> {

    Optional<SessionCart> findSessionCartByCustomerPhoneNumberAndIsOpenTrue(Long phoneNumber);

    Optional<SessionCart> findByCustomer(Customer customer);

    Optional<SessionCart> findSessionCartByUniqueIdAndIsOpenTrue(String uniqueId);

    Optional<SessionCart> findByUniqueId(String uniqueId);

    boolean existsByCustomerAndIsOpenTrue(Customer customer);

    void deleteByUniqueId(String uniqueId);

    @Override
    Page<SessionCart> findAll(Pageable pageable);
}

