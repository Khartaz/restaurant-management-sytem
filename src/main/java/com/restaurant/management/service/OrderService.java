package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.exception.cart.CartException;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.repository.*;
import com.restaurant.management.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private Utils utils;
    private OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        Utils utils,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.utils = utils;
        this.orderMapper = orderMapper;
    }

    public OrderDto processOrder(Long phoneNumber) {

        Cart cart = cartRepository.findCartByCustomerPhoneNumberAndIsOpenTrue(phoneNumber)
                .orElseThrow(() -> new CartException(CartMessages.CART_NOT_FOUND.getErrorMessage()));

        Order order = new Order.OrderBuilder()
                .setOrdered(new Date().toInstant())
                .setStatus("TEST STATUS")
                .setOrderNumber(utils.generateOrderNumber(5))
                .setTotalPrice(cart.calculateTotal())
                .setCart(cart)
                .build();

        order.getCart().setOpen(Boolean.FALSE);

        orderRepository.save(order);

        return orderMapper.mapToOrderDto(order);
    }

}
