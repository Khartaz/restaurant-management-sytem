package com.restaurant.management.repository;

import com.restaurant.management.domain.ecommerce.DailyOrderList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DailyOrderListRepository extends JpaRepository<DailyOrderList, Long> {

    Optional<DailyOrderList> findDailyOrderListByIsOpenTrueAndCompanyId(Long companyId);

    boolean existsByIsOpenTrueAndCompanyId(Long id);

    Optional<DailyOrderList> findByIdAndCompanyId(Long orderListId, Long companyId);

    @Override
    Page<DailyOrderList> findAll(Pageable pageable);

    Page<DailyOrderList> findAllByCompanyId(Long restaurantId, Pageable pageable);

    DailyOrderList findByIsOpenIsTrueAndCompanyId(Long companyId);

}
