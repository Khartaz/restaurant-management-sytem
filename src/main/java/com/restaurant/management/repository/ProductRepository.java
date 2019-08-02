package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.domain.ecommerce.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndCompanyId(Long id, Long companyId);

    void deleteProductById(Long id);

    @Override
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByCompanyAndIsDeletedIsFalse(Pageable pageable, Company company);

}
