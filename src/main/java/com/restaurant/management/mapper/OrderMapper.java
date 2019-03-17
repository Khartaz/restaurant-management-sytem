package com.restaurant.management.mapper;

import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.web.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                cartMapper.mapToCart(orderDto.getCart())
        );
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getOrdered(),
                order.getStatus(),
                order.getTotalPrice(),
                cartMapper.mapToCartDto(order.getCart())
        );
    }

    public OrderResponse mapToOrderResponse(final OrderDto orderDto) {
        return new OrderResponse(
                orderDto.getId(),
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                cartMapper.mapToCartResponse(orderDto.getCart())
        );
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> order) {
        return order.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> mapToOrderResponseList(final List<OrderDto> orders) {
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }
}
