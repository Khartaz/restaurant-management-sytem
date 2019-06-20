package com.restaurant.management.repository;

import com.restaurant.management.domain.DailyOrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyOrderListRepository extends JpaRepository<DailyOrderList, Long> {

    Optional<DailyOrderList> findDailyOrderListByIsOpenTrueAndRestaurantInfoId(Long restaurantId);

    boolean existsByIsOpenTrueAndRestaurantInfoId(Long id);

    Optional<DailyOrderList> findByIdAndRestaurantInfoId(Long orderListId, Long restaurantId);

    @Override
    Page<DailyOrderList> findAll(Pageable pageable);

    Page<DailyOrderList> findAllByRestaurantInfoId(Long restaurantId, Pageable pageable);

    DailyOrderList findByIsOpenIsTrueAndRestaurantInfoId(Long restaurantId);

}
