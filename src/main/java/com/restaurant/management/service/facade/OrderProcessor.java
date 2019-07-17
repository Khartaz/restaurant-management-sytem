package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.DailyOrderList;
import com.restaurant.management.domain.ecommerce.Order;
import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OrderProcessor {
    private OrderService orderService;
    private DailyOrderListService dailyOrderListService;
    private OrderMapper orderMapper;
    private DailyOrderListMapper dailyOrderListMapper;

    @Autowired
    public OrderProcessor(OrderService orderService,
                          DailyOrderListService dailyOrderListService,
                          OrderMapper orderMapper,
                          DailyOrderListMapper dailyOrderListMapper) {
        this.orderService = orderService;
        this.dailyOrderListService = dailyOrderListService;
        this.orderMapper = orderMapper;
        this.dailyOrderListMapper = dailyOrderListMapper;
    }

    public DailyOrderListDto processOrder(@CurrentUser UserPrincipal currentUser, Long customerId) {

        if (!dailyOrderListService.checkDailyOrderListExists(currentUser)) {

            dailyOrderListService.openOrderList(currentUser);
        }

        Order order = orderService.processOrder(currentUser, customerId);

        DailyOrderList orderList = dailyOrderListService.addOrderToList(currentUser, order.getId());

        return dailyOrderListMapper.mapToDailyOrderListDto(orderList);
    }

}
