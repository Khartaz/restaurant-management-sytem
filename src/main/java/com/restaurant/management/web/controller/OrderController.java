package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/{phoneNumber}")
    public @ResponseBody
    ResponseEntity<OrderDto> sendOrder(@PathVariable Long phoneNumber) {

        return ResponseEntity.ok(orderService.processOrder(phoneNumber));
    }
}
