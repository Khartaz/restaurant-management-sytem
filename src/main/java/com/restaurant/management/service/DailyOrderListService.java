package com.restaurant.management.service;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailyOrderListService {

    DailyOrderList getOrderListById(Long orderListId);

    Page<DailyOrderList> getAll(Pageable pageable);

    DailyOrderList openOrderList();

    DailyOrderList getOpenedOrderList();

    DailyOrderList addOrderToList(Long orderId);

    DailyOrderList removeOrderFromList(Long orderId);

    DailyOrderList closeDailyList();

    ApiResponse deleteById(Long orderListId);
}
