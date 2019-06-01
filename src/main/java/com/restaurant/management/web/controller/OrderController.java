package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.response.OrderResponse;
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

import javax.annotation.security.RolesAllowed;

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
    ResponseEntity<PagedResources<OrderResponse>> showOrdersPageable(@CurrentUser UserPrincipal currentUser,
                                                                     Pageable pageable,
                                                                     PagedResourcesAssembler assembler) {
        Page<OrderDto> ordersDto = orderFacade.getAllOrders(currentUser, pageable);

        Page<OrderResponse> responsePage = orderMapper.mapToOrderResponsePage(ordersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/count", produces = APPLICATION_JSON_VALUE)
    public Long countOrders(@CurrentUser UserPrincipal currentUser){
        return orderFacade.countRestaurantOrders(currentUser);
    }

    @GetMapping(value = "/{orderId}",
            produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> showOrder(@CurrentUser UserPrincipal currentUser,
                                      @PathVariable Long orderId) {
        OrderDto orderDto = orderFacade.getByOrderNumber(currentUser, orderId);

        OrderResponse response = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{orderId}")
    public ResponseEntity<?> deleteOrder(@CurrentUser UserPrincipal currentUser,
                                         @PathVariable Long orderId) {

        return ResponseEntity.ok().body(orderFacade.deleteOrder(currentUser, orderId));
    }

    @GetMapping(value = "/year", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<OrderResponse>> getAllOfCurrentYear(@CurrentUser UserPrincipal currentUser,
                                                                      Pageable pageable,
                                                                      PagedResourcesAssembler assembler) {
        Page<OrderDto> ordersDto = orderFacade.getAllOfCurrentYear(currentUser, pageable);

        Page<OrderResponse> ordersResponse = orderMapper.mapToOrderResponsePage(ordersDto);

        return new ResponseEntity<>(assembler.toResource(ordersResponse), HttpStatus.OK);
    }

    @PostMapping(value = "/order/{customerId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> processOrder(@CurrentUser UserPrincipal currentUser,
                                         @PathVariable Long customerId) {
        OrderDto orderDto = orderFacade.processOrder(currentUser, customerId);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(OrderController.class).slash(orderResponse.getId()).withSelfRel();

        return new Resource<>(orderResponse, link);
    }
}