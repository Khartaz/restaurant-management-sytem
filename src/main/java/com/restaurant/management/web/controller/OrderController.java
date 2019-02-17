package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.web.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping(value = "/{phoneNumber}")
    public @ResponseBody
    Resource<OrderResponse> sendOrder(@PathVariable Long phoneNumber) {

        OrderDto orderDto = orderService.processOrder(phoneNumber);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(orderResponse.getOrderNumber()).withSelfRel();
        return new Resource<>(orderResponse, link);
    }
}
