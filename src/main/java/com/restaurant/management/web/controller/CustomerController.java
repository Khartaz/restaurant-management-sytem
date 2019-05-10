package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.CartFacade;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.CartResponse;
import com.restaurant.management.web.response.CustomerResponse;
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

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customers")
@SuppressWarnings("Duplicates")
public class CustomerController {

    private CustomerFacade customerFacade;
    private CustomerMapper customerMapper;
    private CartFacade cartFacade;
    private CartMapper cartMapper;
    private OrderFacade orderFacade;
    private OrderMapper orderMapper;

    @Autowired
    public CustomerController(CustomerFacade customerFacade,
                              CustomerMapper customerMapper,
                              CartFacade cartFacade,
                              CartMapper cartMapper,
                              OrderFacade orderFacade,
                              OrderMapper orderMapper) {
        this.customerFacade = customerFacade;
        this.customerMapper = customerMapper;
        this.cartFacade = cartFacade;
        this.cartMapper = cartMapper;
        this.orderFacade = orderFacade;
        this.orderMapper = orderMapper;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> registerCustomer(@CurrentUser UserPrincipal currentUser,
                                                @Valid @RequestBody SignUpCustomerRequest signUpCustomerRequest) {
        CustomerDto customerDto = customerFacade.createCustomer(currentUser, signUpCustomerRequest);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomerById(@CurrentUser UserPrincipal currentUser,
                                                @PathVariable Long id) {
        return ResponseEntity.ok().body(customerFacade.deleteCustomerById(currentUser, id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerResponse>> getAllCustomersPageable(@CurrentUser UserPrincipal currentUser,
                                                                             Pageable pageable,
                                                                             PagedResourcesAssembler assembler) {
        Page<CustomerDto> customersDto = customerFacade.getAllCustomers(currentUser, pageable);

        Page<CustomerResponse> responsePage = customerMapper.mapToCustomerResponsePage(customersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> showCustomer(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
        CustomerDto customerDto = customerFacade.getCustomerById(currentUser, id);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PostMapping(value = "/{id}/carts/session", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> registerCustomerCart(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
        CartDto cartDto = cartFacade.openSessionCart(currentUser, id);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @PutMapping(value = "/{customerId}/carts/session",
            produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> addToSessionCart(@CurrentUser UserPrincipal currentUser,
                                            @PathVariable Long customerId,
                                            @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.addToCart(currentUser, customerId, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(customerId).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @PutMapping(value = "/{id}/carts/session/product",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> updateProductQuantity(@CurrentUser UserPrincipal currentUser,
                                                 @PathVariable Long id,
                                                 @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.updateProductQuantity(currentUser, id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session/product").withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}/carts/session/product",
            produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> removeProductCart(@CurrentUser UserPrincipal currentUser,
                                             @PathVariable Long id,
                                             @Valid @RequestBody RemoveProductRequest request) {
        CartDto cartDto = cartFacade.removeProductFromCart(currentUser, id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/{id}/carts/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerSessionCart(@CurrentUser UserPrincipal currentUser,
                                                   @PathVariable Long id) {
        CartDto cartDto = cartFacade.getSessionCartByCustomerId(currentUser, id);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/{id}/carts", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CartResponse>> showCustomerCarts
            (@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<CartDto> cartsDto = cartFacade.getCustomerCarts(id, pageable);

        Page<CartResponse> response = cartMapper.mapToCartResponsePage(cartsDto);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/carts/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerCart(@PathVariable Long id, @PathVariable String uniqueId) {
        CartDto cartDto = cartFacade.getCustomerCartByUniqueId(id, uniqueId);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts").slash(cartResponse.getUniqueId()).withSelfRel();

        return new Resource<>(cartResponse, link);
    }

    @PostMapping(value = "{id}/orders", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> processSessionCartToOrder(@PathVariable Long id) {
        OrderDto orderDto = orderFacade.processOrder(id);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("orders").slash(orderResponse.getOrderNumber()).withSelfRel();

        return new Resource<>(orderResponse, link);
    }

    @GetMapping(value = "{id}/orders", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<OrderResponse>> showCustomerOrders
            (@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<OrderDto> ordersDto = orderFacade.getOrdersByCustomerId(id, pageable);

        Page<OrderResponse> ordersResponse = orderMapper.mapToOrderResponsePage(ordersDto);

        return new ResponseEntity<>(assembler.toResource(ordersResponse), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/orders/{orderNumber}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> showCustomerOrder(@PathVariable Long id, @PathVariable String orderNumber) {
        OrderDto ordersDto = orderFacade.getOrderByCustomerIdAndOrderNumber(id, orderNumber);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(ordersDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("orders").slash(orderResponse.getOrderNumber()).withSelfRel();

        return new Resource<>(orderResponse, link);
    }
}
