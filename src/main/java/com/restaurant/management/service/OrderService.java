package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.exception.cart.CartNotFoundException;
import com.restaurant.management.exception.cart.CartMessages;
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
    private CartRepository cartRepository;
    private Utils utils;
    private OrderMapper orderMapper;
    private DailyOrderListRepository dailyOrderListRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        Utils utils,
                        OrderMapper orderMapper,
                        DailyOrderListRepository dailyOrderListRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.utils = utils;
        this.orderMapper = orderMapper;
        this.dailyOrderListRepository = dailyOrderListRepository;
    }

    public OrderDto processOrder(Long phoneNumber) {

        Cart cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber)
                .orElseThrow(() -> new CartNotFoundException(CartMessages.CART_NOT_FOUND.getMessage()));

        Order order = new Order.OrderBuilder()
                .setOrdered(new Date().toInstant())
                .setStatus("ORDERED") //Delivered? enum
                .setOrderNumber(utils.generateOrderNumber(5))
                .setTotalPrice(cart.calculateTotal())
                .setCart(cart)
                .build();

        order.getCart().setOpen(Boolean.FALSE);

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
