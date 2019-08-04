package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhoneNumberAndCompanyId(String phoneNumber, Long companyId);

    Boolean existsByEmailAndCompanyId(String email, Long companyId);

    Boolean existsByIdAndCompanyId(Long id, Long companyId);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByIdAndCompanyId(Long id, Long companyId);

    void deleteById(Long id);

    Page<Customer> findAllByCompanyIdAndIsDeletedIsFalse(Pageable pageable, Long companyId);

    Page<Customer> findAllByNameIsContainingAndCompanyId(String name, Long companyId, Pageable pageable);

    Page<Customer> findAllByPhoneNumberIsContainingAndCompanyId(String phoneNumber, Long companyId, Pageable pageable);

    Page<Customer> findAllByLastnameContainingAndCompanyId(String lastname, Long companyId, Pageable pageable);

}
