package com.restaurant.management.service.impl;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.exception.order.OrderListExistsException;
import com.restaurant.management.exception.order.OrderListNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.repository.DailyOrderListRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class DailyOrderListServiceImpl implements DailyOrderListService {
    private DailyOrderListRepository dailyOrderListRepository;
    private OrderRepository orderRepository;

    @Autowired
    public DailyOrderListServiceImpl(DailyOrderListRepository dailyOrderListRepository,
                                     OrderRepository orderRepository) {
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.orderRepository = orderRepository;
    }

    public DailyOrderList getOrderListById(Long orderListId) {
        return dailyOrderListRepository.findById(orderListId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public Page<DailyOrderList> getAll(Pageable pageable) {
        return dailyOrderListRepository.findAll(pageable);
    }

    public DailyOrderList openOrderList() {
        if (dailyOrderListRepository.existsByIsOpenTrue()) {
            throw new OrderListExistsException(OrderMessages.ORDER_LIST_EXISTS.getMessage());
        }

        DailyOrderList orderList = new DailyOrderList();

        orderList.setDailyIncome(0.00);
        orderList.setNumberOfOrders(0);
        orderList.setOpened(Boolean.TRUE);
        orderList.setOrders(new LinkedHashSet<>());

        dailyOrderListRepository.save(orderList);

        return orderList;
    }

    public DailyOrderList getOpenedOrderList() {
        return dailyOrderListRepository.findDailyOrderListByIsOpenTrue()
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public DailyOrderList addOrderToList(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = getOpenedOrderList();

        int oldSize = dailyOrderList.getOrders().size();

        Set<Order> orders  = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.add(order);

        if (orders.size() >= oldSize) {
            double income = order.getTotalPrice() + dailyOrderList.getDailyIncome();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
            dailyOrderList.setNumberOfOrders(orders.size());
            dailyOrderList.setOrders(orders);
        }

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public DailyOrderList removeOrderFromList(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = getOpenedOrderList();

        int oldSize = dailyOrderList.getOrders().size();

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.remove(order);

        if (orders.size() <= oldSize) {
            double income = dailyOrderList.getDailyIncome() - order.getTotalPrice();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
            dailyOrderList.setNumberOfOrders(orders.size());
            dailyOrderList.setOrders(orders);
        }

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public DailyOrderList closeDailyList() {
        DailyOrderList dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenTrue()
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_OPEN.getMessage()));

        dailyOrderList.setOpened(Boolean.FALSE);
        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public ApiResponse deleteById(Long orderListId) {
        DailyOrderList orderList = dailyOrderListRepository.findById(orderListId)
                .orElseThrow(() -> new OrderListExistsException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));

        dailyOrderListRepository.delete(orderList);

        return new ApiResponse(true, OrderMessages.ORDER_LIST_DELETED.getMessage());

    }

}
