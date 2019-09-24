package com.restaurant.management.repository.ecommerce;

import com.restaurant.management.domain.ecommerce.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);
}
