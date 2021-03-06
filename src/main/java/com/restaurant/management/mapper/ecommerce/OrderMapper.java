package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.Order;
import com.restaurant.management.domain.ecommerce.dto.OrderDto;
import com.restaurant.management.web.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class OrderMapper {

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    public Order mapToOrder(final OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getOrderNumber(),
                orderDto.getOrderStatus(),
                orderDto.getAssignedToUserId(),
                orderDto.getOrderType(),
                cartMapper.mapToCartOrdered(orderDto.getCart())
        );
    }

    public OrderDto mapToOrderDto(final Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getAssignedToUserId(),
                order.getOrderType(),
                cartMapper.mapToCartDto(order.getCartOrdered())
        );
    }

    public OrderResponse mapToOrderResponse(final OrderDto orderDto) {
        return new OrderResponse(
                orderDto.getId(),
                orderDto.getOrderNumber(),
                orderDto.getOrderStatus(),
                orderDto.getAssignedToUserId(),
                orderDto.getOrderType(),
                cartMapper.mapToCartResponse(orderDto.getCart())
        );
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> order) {
        return order.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }

    public Page<OrderDto> mapToOrderDtoPage(final Page<Order> orders) {
        return orders.map(this::mapToOrderDto);
    }

    public List<OrderResponse> mapToOrderResponseList(final List<OrderDto> orders) {
        return orders.stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public Page<OrderResponse> mapToOrderResponsePage(final Page<OrderDto> orders) {
        return orders.map(this::mapToOrderResponse);
    }

}
