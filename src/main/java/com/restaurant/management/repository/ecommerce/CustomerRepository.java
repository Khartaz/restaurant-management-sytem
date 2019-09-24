package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByPhoneAndCompanyIdAndIsDeletedIsFalse(String phone, Long companyId);

    Boolean existsByEmailAndCompanyIdAndIsDeletedIsFalse(String email, Long companyId);

    Boolean existsByIdAndCompanyId(Long id, Long companyId);

    Optional<Customer> findById(Long id);

    List<Customer> findAllByIdIn(List<Long> customersIds);

    Optional<Customer> findByIdAndCompanyIdAndIsDeletedIsFalse(Long customerId, Long companyId);

    void deleteById(Long id);

    Page<Customer> findAllByCompanyAndIsDeletedIsFalse(Pageable pageable, Company company);

    Page<Customer> findAllByNameIsContainingAndCompanyId(String name, Long companyId, Pageable pageable);

    Page<Customer> findAllByPhoneIsContainingAndCompanyId(String phone, Long companyId, Pageable pageable);

    Page<Customer> findAllByLastNameContainingAndCompanyId(String lastName, Long companyId, Pageable pageable);

}
