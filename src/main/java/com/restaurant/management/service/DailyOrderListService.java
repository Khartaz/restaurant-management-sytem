package com.restaurant.management.service;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.restaurant.StatisticsReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DailyOrderListService {

    DailyOrderList getOrderListById(@CurrentUser UserPrincipal currentUser, Long orderListId);

    Page<DailyOrderList> getAll(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    DailyOrderList openOrderList(@CurrentUser UserPrincipal currentUser);

    DailyOrderList getOpenedOrderList(@CurrentUser UserPrincipal currentUser);

    DailyOrderList addOrderToList(@CurrentUser UserPrincipal currentUser, Long orderId);

    DailyOrderList removeOrderFromList(@CurrentUser UserPrincipal currentUser, Long orderId);

    DailyOrderList closeDailyList(@CurrentUser UserPrincipal currentUser);

    ApiResponse deleteById(@CurrentUser UserPrincipal currentUser, Long orderListId);

    StatisticsReportResponse countDailyOrders(@CurrentUser UserPrincipal currentUser);
}
