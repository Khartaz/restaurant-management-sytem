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
import com.restaurant.management.service.facade.SessionCartFacade;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.request.account.SignUpCustomerRequest;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customers")
@SuppressWarnings("Duplicates")
public class CustomerController {

    private CustomerFacade customerFacade;
    private CustomerMapper customerMapper;
    private SessionCartFacade sessionCartFacade;
    private CartMapper cartMapper;
    private OrderFacade orderFacade;
    private OrderMapper orderMapper;
    private CartFacade cartFacade;

    @Autowired
    public CustomerController(CustomerFacade customerFacade,
                              CustomerMapper customerMapper,
                              SessionCartFacade sessionCartFacade,
                              CartMapper cartMapper,
                              OrderFacade orderFacade,
                              OrderMapper orderMapper,
                              CartFacade cartFacade) {
        this.customerFacade = customerFacade;
        this.customerMapper = customerMapper;
        this.sessionCartFacade = sessionCartFacade;
        this.cartMapper = cartMapper;
        this.orderFacade = orderFacade;
        this.orderMapper = orderMapper;
        this.cartFacade = cartFacade;
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

    @PostMapping(value = "/{id}/carts/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> registerCustomerCart(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
        CartDto cartDto = sessionCartFacade.openSessionCart(currentUser, id);

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
        CartDto cartDto = sessionCartFacade.addToCart(currentUser, customerId, request);

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
        CartDto cartDto = sessionCartFacade.updateProductQuantity(currentUser, id, request);

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
        CartDto cartDto = sessionCartFacade.removeProductFromCart(currentUser, id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/{id}/carts/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerSessionCart(@CurrentUser UserPrincipal currentUser,
                                                   @PathVariable Long id) {
        CartDto cartDto = sessionCartFacade.getSessionCartByCustomerId(currentUser, id);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/{id}/carts", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CartResponse>> showCustomerCarts(@CurrentUser UserPrincipal currentUser,
                                                                   @PathVariable Long id, Pageable pageable,
                                                                   PagedResourcesAssembler assembler) {
        Page<CartDto> cartsDto = cartFacade.getCustomerCarts(currentUser, id, pageable);

        Page<CartResponse> response = cartMapper.mapToCartResponsePage(cartsDto);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{customerId}/carts/{cartId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerCart(@CurrentUser UserPrincipal currentUser,
                                            @PathVariable Long customerId,
                                            @PathVariable Long cartId) {
        CartDto cartDto = cartFacade.getCustomerCartById(currentUser, customerId, cartId);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(customerId).slash("carts").slash(cartResponse.getId()).withSelfRel();

        return new Resource<>(cartResponse, link);
    }

    @PostMapping(value = "{customerId}/orders", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> processSessionCartToOrder(@CurrentUser UserPrincipal currentUser,
                                                      @PathVariable Long customerId) {
        OrderDto orderDto = orderFacade.processOrder(currentUser, customerId);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(orderDto);

        Link link = linkTo(CustomerController.class).slash(customerId).slash("orders").slash(orderResponse.getOrderNumber()).withSelfRel();

        return new Resource<>(orderResponse, link);
    }

    @GetMapping(value = "{customerId}/orders", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<OrderResponse>> showCustomerOrders(@CurrentUser UserPrincipal currentUser,
                                                                     @PathVariable Long customerId,
                                                                     Pageable pageable,
                                                                     PagedResourcesAssembler assembler) {
        Page<OrderDto> ordersDto = orderFacade.getCustomerOrdersById(currentUser, customerId, pageable);

        Page<OrderResponse> ordersResponse = orderMapper.mapToOrderResponsePage(ordersDto);

        return new ResponseEntity<>(assembler.toResource(ordersResponse), HttpStatus.OK);
    }

    @GetMapping(value = "{customerId}/orders/{orderId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<OrderResponse> showCustomerOrder(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable Long customerId,
                                              @PathVariable Long orderId) {
        OrderDto ordersDto = orderFacade.getOrderByCustomerIdAndOrderId(currentUser, customerId, orderId);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(ordersDto);

        Link link = linkTo(CustomerController.class).slash(customerId).slash("orders").slash(orderResponse.getId()).withSelfRel();

        return new Resource<>(orderResponse, link);
    }
}
