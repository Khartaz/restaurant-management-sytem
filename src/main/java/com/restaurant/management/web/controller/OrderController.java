package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.response.OrderResponse;
import com.restaurant.management.web.response.SendOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderFacade orderFacade;
    private OrderMapper orderMapper;

    @Autowired
    public OrderController(OrderFacade orderFacade, OrderMapper orderMapper) {
        this.orderFacade = orderFacade;
        this.orderMapper = orderMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<OrderResponse>> showOrdersPageable(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<OrderDto> ordersDto = orderFacade.getAllOrders(pageable);

        Page<OrderResponse> responsePage = orderMapper.mapToOrderResponsePage(ordersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{orderNumber}",
            produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> showOrder(@PathVariable String orderNumber) {
        OrderDto orderDto = orderFacade.getByOrderNumber(orderNumber);

        OrderResponse response = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(response.getOrderNumber()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{orderNumber}")
    public ResponseEntity<?> deleteOrder(@PathVariable String orderNumber) {

        return ResponseEntity.ok().body(orderFacade.deleteOrder(orderNumber));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> registerOrder(@RequestBody SendOrder sendOrder) {

        OrderDto orderDto = orderFacade.processOrder(sendOrder);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(orderResponse.getOrderNumber()).withSelfRel();
        return new Resource<>(orderResponse, link);
    }

}