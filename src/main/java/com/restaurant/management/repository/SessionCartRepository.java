package com.restaurant.management.repository;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findCartByCustomerPhoneNumberAndIsOpenTrue(Long phoneNumber);

    Optional<List<Cart>> findAllByIsOpenIsTrue();

    Optional<List<Cart>> findAllByIsOpenIsFalse();

    List<Cart> findByCustomer(Customer customer);

    void deleteAllByCustomer(Customer customer);

    Optional<Cart> findCartByUniqueIdAndIsOpenTrue(String uniqueId);

    Optional<Cart> findByUniqueId(String uniqueId);

    boolean existsByCustomerAndIsOpenTrue(Customer customer);

}

