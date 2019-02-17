package com.restaurant.management.mapper;

import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                orderDto.getCart()
        );
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getOrdered(),
                order.getStatus(),
                order.getTotalPrice(),
                order.getCart()
        );
    }
}
