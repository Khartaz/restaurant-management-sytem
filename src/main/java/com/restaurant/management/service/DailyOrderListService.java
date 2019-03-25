package com.restaurant.management.service;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.exception.order.OrderListExistsException;
import com.restaurant.management.exception.order.OrderListNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.repository.DailyOrderListRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class DailyOrderListService {
    private DailyOrderListRepository dailyOrderListRepository;
    private OrderRepository orderRepository;

    @Autowired
    public DailyOrderListService(DailyOrderListRepository dailyOrderListRepository,
                                 OrderRepository orderRepository) {
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.orderRepository = orderRepository;
    }

    public DailyOrderList getOrderListByUniqueId(String uniqueId) {
        return dailyOrderListRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public List<DailyOrderList> getAll() {
        return dailyOrderListRepository.findAll();
    }

    public DailyOrderList openOrderList() {
        if (dailyOrderListRepository.existsByIsOpenTrue()) {
            throw new OrderListExistsException(OrderMessages.ORDER_LIST_EXISTS.getMessage());
        }

        DailyOrderList orderList = new DailyOrderList();

        orderList.setUniqueId(Utils.generateDailyOrderListUniqueId(10));
        orderList.setDailyIncome(0.00);
        orderList.setOpened(Boolean.TRUE);
        orderList.setOrders(new LinkedHashSet<>());

        dailyOrderListRepository.save(orderList);

        return orderList;
    }

    public DailyOrderList getOpenedOrderList() {
        return dailyOrderListRepository.findDailyOrderListByIsOpenTrue()
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public DailyOrderList addOrderToList(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = getOpenedOrderList();

        int oldSize = dailyOrderList.getOrders().size();

        Set<Order> orders  = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.add(order);

        if (orders.size() >= oldSize) {
            double income = order.getTotalPrice() + dailyOrderList.getDailyIncome();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
            dailyOrderList.setOrders(orders);
        }

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public DailyOrderList removeOrderFromList(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = getOpenedOrderList();

        int oldSize = dailyOrderList.getOrders().size();

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.remove(order);

        if (orders.size() <= oldSize) {
            double income = dailyOrderList.getDailyIncome() - order.getTotalPrice();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
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

    public ApiResponse deleteByUniqueId(String uniqueId) {
        Optional<DailyOrderList> orderList = dailyOrderListRepository.findByUniqueId(uniqueId);

        if (orderList.isPresent()) {
            dailyOrderListRepository.delete(orderList.get());

            return new ApiResponse(true, OrderMessages.ORDER_LIST_DELETED.getMessage());

        } else {
            throw new OrderListExistsException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage());
        }
    }

}
