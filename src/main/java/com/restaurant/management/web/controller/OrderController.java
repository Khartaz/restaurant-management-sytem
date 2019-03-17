package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.OrderService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.OrderResponse;
import com.restaurant.management.web.response.SendOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<OrderResponse> showOrders() {
        List<OrderDto> ordersDto = orderService.showOrders();

        List<OrderResponse> response = orderMapper.mapToOrderResponseList(ordersDto);

        Link link = linkTo(OrderController.class).withSelfRel();

        return new Resources<>(response, link);
    }

    @GetMapping(value = "/{orderNumber}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> showOrder(@PathVariable String orderNumber) {
        OrderDto orderDto = orderService.getByOrderNumber(orderNumber);

        OrderResponse response = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(response.getOrderNumber()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{orderNumber}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderNumber) {
        orderService.deleteOrder(orderNumber);
        return ResponseEntity.ok().body(new ApiResponse(true, OrderMessages.ORDER_DELETED.getMessage()));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> registerOrder(@RequestBody SendOrder sendOrder) {

        OrderDto orderDto = orderService.processOrder(sendOrder.getPhoneNumber());

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(orderResponse.getOrderNumber()).withSelfRel();
        return new Resource<>(orderResponse, link);
    }

}