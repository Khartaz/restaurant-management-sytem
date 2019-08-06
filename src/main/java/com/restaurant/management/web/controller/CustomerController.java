package com.restaurant.management.web.controller;

import com.restaurant.management.domain.ecommerce.dto.CartDto;
import com.restaurant.management.domain.ecommerce.dto.CustomerDto;
import com.restaurant.management.domain.ecommerce.dto.CustomerFormDTO;
import com.restaurant.management.domain.ecommerce.dto.OrderDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.SessionCartFacade;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
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

    @Autowired
    public CustomerController(CustomerFacade customerFacade,
                              CustomerMapper customerMapper,
                              SessionCartFacade sessionCartFacade,
                              CartMapper cartMapper,
                              OrderFacade orderFacade,
                              OrderMapper orderMapper) {
        this.customerFacade = customerFacade;
        this.customerMapper = customerMapper;
        this.sessionCartFacade = sessionCartFacade;
        this.cartMapper = cartMapper;
        this.orderFacade = orderFacade;
        this.orderMapper = orderMapper;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerDto> registerCustomer(@CurrentUser UserPrincipal currentUser,
                                           @Valid @RequestBody CustomerFormDTO customerFormDTO) {
        CustomerDto customerDto = customerFacade.registerCustomer(currentUser, customerFormDTO);

        Link link = linkTo(CustomerController.class).slash(customerDto.getId()).withSelfRel();

        return new Resource<>(customerDto, link);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerFormDTO> updateCustomer(@CurrentUser UserPrincipal currentUser,
                                             @Valid @RequestBody CustomerFormDTO request) {

        CustomerFormDTO customerFormDTO = customerFacade.updateCustomer(currentUser, request);

        Link link = linkTo(CustomerController.class).slash(customerFormDTO.getId()).withSelfRel();

        return new Resource<>(customerFormDTO, link);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@CurrentUser UserPrincipal currentUser,
                                                @PathVariable Long customerId) {
        return ResponseEntity.ok().body(customerFacade.deleteCustomerById(currentUser, customerId));
    }

    @DeleteMapping(value = "/delete/{customerIds}")
    public ResponseEntity<?> deleteAllById(@CurrentUser UserPrincipal currentUser,
                                           @PathVariable Long[] customerIds) {

        return ResponseEntity.ok().body(customerFacade.deleteAllByIds(currentUser, customerIds));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerFormDTO>> getAllCustomersPageable(@CurrentUser UserPrincipal currentUser,
                                                                            Pageable pageable,
                                                                            PagedResourcesAssembler assembler) {
        Page<CustomerFormDTO> customerFormDTO = customerFacade.getAllCustomers(currentUser, pageable);

        if (!customerFormDTO.hasContent()) {
            PagedResources pagedResources = assembler.toEmptyResource(customerFormDTO, CustomerFormDTO.class);
            return new ResponseEntity<PagedResources<CustomerFormDTO>>(pagedResources, HttpStatus.OK);
        }

        return new ResponseEntity<>(assembler.toResource(customerFormDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/checkPhoneAvailability", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody ApiResponse checkCustomerPhoneAvailability(@CurrentUser UserPrincipal currentUser,
                                                                                    @RequestParam String phone) {
        return customerFacade.checkCustomerPhoneAvailability(currentUser, phone);
    }

    @GetMapping(value = "/name", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerResponse>> getAllCustomersByName(@CurrentUser UserPrincipal currentUser,
                                                                           Pageable pageable,
                                                                           PagedResourcesAssembler assembler,
                                                                           @RequestParam String name) {

        Page<CustomerDto> customersDto = customerFacade.getAllCustomersWithNameWithin(currentUser, name, pageable);

        Page<CustomerResponse> responsePage = customerMapper.mapToCustomerResponsePage(customersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/lastName", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerResponse>> getAllCustomersByLastName(@CurrentUser UserPrincipal currentUser,
                                                                               Pageable pageable,
                                                                               PagedResourcesAssembler assembler,
                                                                               @RequestParam String lastName) {

        Page<CustomerDto> customersDto = customerFacade.getAllCustomersByLastNameWithin(currentUser, lastName, pageable);

        Page<CustomerResponse> responsePage = customerMapper.mapToCustomerResponsePage(customersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/phone", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerResponse>> getAllCustomersByPhoneNumber(@CurrentUser UserPrincipal currentUser,
                                                                                  Pageable pageable,
                                                                                  PagedResourcesAssembler assembler,
                                                                                  @RequestParam String phone) {

        Page<CustomerDto> customersDto = customerFacade.getAllCustomersWithPhoneWithin(currentUser, phone, pageable);

        Page<CustomerResponse> responsePage = customerMapper.mapToCustomerResponsePage(customersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerFormDTO> showCustomer(@CurrentUser UserPrincipal currentUser, @PathVariable Long id) {
        CustomerFormDTO customerFormDTO = customerFacade.getCustomerById(currentUser, id);

        Link link = linkTo(CustomerController.class).slash(customerFormDTO.getId()).withSelfRel();

        return new Resource<>(customerFormDTO, link);
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
