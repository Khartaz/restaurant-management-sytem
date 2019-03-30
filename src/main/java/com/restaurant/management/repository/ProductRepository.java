package com.restaurant.management.repository;

import com.restaurant.management.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByUniqueId(String id);

    boolean existsByName(String name);

    void deleteByUniqueId(String uniqueId);

    Optional<Product> findProductByName(String name);

    @Override
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByNameStartingWith(String name);

    Optional<Product> findByCategoryStartingWith(String category);

    List<Product> findAllByName(String name, Pageable pageable);

    List<Product> findAllByCategory(String category, Pageable pageable);

    List<Product> findAllByPrice(Double price, Pageable pageable);
}
