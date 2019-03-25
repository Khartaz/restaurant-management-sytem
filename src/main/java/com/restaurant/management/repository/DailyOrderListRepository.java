package com.restaurant.management.repository;

import com.restaurant.management.domain.DailyOrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DailyOrderListRepository extends JpaRepository<DailyOrderList, Long> {

    Optional<DailyOrderList> findDailyOrderListByIsOpenTrue();

    boolean existsByIsOpenTrue();

    Optional<DailyOrderList> findByUniqueId(String uniqueId);

    @Override
    List<DailyOrderList> findAll();

}
