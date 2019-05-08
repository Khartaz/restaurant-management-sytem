package com.restaurant.management.repository;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.RestaurantInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByIdAndRestaurantInfoId(Long id, Long restaurantInfoId);

    boolean existsByName(String name);

    void deleteProductById(Long id);

    Optional<Product> findProductByName(String name);

    @Override
    Page<Product> findAll(Pageable pageable);

    List<Product> findAllByName(String name, Pageable pageable);

    Page<Product> findByRestaurantInfo(Pageable pageable, RestaurantInfo restaurantInfo);

}
