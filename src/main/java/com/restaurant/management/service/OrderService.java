package com.restaurant.management.service;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.exception.orderlist.OrderListNotFoundException;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private Utils utils;
    private OrderMapper orderMapper;
    private DailyOrderListRepository dailyOrderListRepository;
    private CartService cartService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        Utils utils,
                        OrderMapper orderMapper,
                        DailyOrderListRepository dailyOrderListRepository,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.utils = utils;
        this.orderMapper = orderMapper;
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.cartService = cartService;
    }

    public OrderDto processOrder(Long phoneNumber) {
        Cart cart = cartService.confirmCart(phoneNumber);

        Order order = new Order.OrderBuilder()
                .setOrdered(new Date().toInstant())
                .setStatus("ORDERED")
                .setOrderNumber(utils.generateOrderNumber(5))
                .setTotalPrice(new Order().calculateTotalPrice(cart))
                .setCart(cart)
                .build();

        addToDailyOrderList(order);

        orderRepository.save(order);

        return orderMapper.mapToOrderDto(order);
    }


    public void addToDailyOrderList(Order order) {

        Optional<DailyOrderList> dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenedTrue();

        if (dailyOrderList.isPresent()) {
            DailyOrderList list = dailyOrderList.get();
            list.getOrders().add(order);
            list.setDailyIncome(order.getTotalPrice() + list.getDailyIncome());
            dailyOrderListRepository.save(list);
        }

        if (!dailyOrderList.isPresent()) {
            DailyOrderList newList = new DailyOrderList();
            newList.getOrders().add(order);
            newList.setDailyIncome(order.getTotalPrice());
            newList.setOpened(Boolean.TRUE);
            dailyOrderListRepository.save(newList);
        }
    }

    public DailyOrderList closeDailyList() {
        DailyOrderList dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenedTrue()
                .orElseThrow(() -> new OrderListNotFoundException("Order required to close daily list."));

        dailyOrderList.setOpened(Boolean.FALSE);
        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

}
