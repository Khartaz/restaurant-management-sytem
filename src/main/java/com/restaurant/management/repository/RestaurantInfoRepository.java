package com.restaurant.management.repository;

import com.restaurant.management.domain.RestaurantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantInfoRepository extends JpaRepository<RestaurantInfo, Long> {

    Optional<RestaurantInfo> findById(Long id);
}
