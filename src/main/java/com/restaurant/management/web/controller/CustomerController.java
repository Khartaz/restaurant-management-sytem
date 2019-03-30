package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.facade.CartFacade;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.CartResponse;
import com.restaurant.management.web.response.CustomerResponse;
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
public class CustomerController {

    private CustomerFacade customerFacade;
    private CustomerMapper customerMapper;
    private CartFacade cartFacade;
    private CartMapper cartMapper;

    @Autowired
    public CustomerController(CustomerFacade customerFacade,
                              CustomerMapper customerMapper,
                              CartFacade cartFacade,
                              CartMapper cartMapper) {
        this.customerFacade = customerFacade;
        this.customerMapper = customerMapper;
        this.cartFacade = cartFacade;
        this.cartMapper = cartMapper;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> registerCustomer(@Valid @RequestBody SignUpCustomerRequest signUpCustomerRequest) {
        CustomerDto customerDto = customerFacade.createCustomer(signUpCustomerRequest);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerFacade.deleteCustomerById(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CustomerResponse>> getAllCustomersPageable(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<CustomerDto> customersDto = customerFacade.getAllCustomers(pageable);

        Page<CustomerResponse> responsePage = customerMapper.mapToCustomerResponsePage(customersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CustomerResponse> showCustomer(@PathVariable Long id) {
        CustomerDto customerDto = customerFacade.getCustomerById(id);

        CustomerResponse response = customerMapper.mapToCustomerResponse(customerDto);

        Link link = linkTo(CustomerController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PostMapping(value = "/{id}/carts/session", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> registerCustomerCart(@PathVariable Long id) {
        CartDto cartDto = cartFacade.openSessionCart(id);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @PutMapping(value = "/{id}/carts/session",
            produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> addToCart(@PathVariable Long id, @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.addToCart(id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @PutMapping(value = "/{id}/carts/session/product",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> updateProductQuantity(@PathVariable Long id, @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.updateProductQuantity(id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session/product").withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}/carts/session/product",
            produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> removeProductCart(@PathVariable Long id, @Valid @RequestBody RemoveProductRequest request) {
        CartDto cartDto = cartFacade.removeProductFromCart(id, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "{id}/carts/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerSessionCart(@PathVariable Long id) {
        CartDto cartDto = cartFacade.getSessionCartByCustomerId(id);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts/session").withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "{id}/carts", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CartResponse>> showCustomerCarts
            (@PathVariable Long id, Pageable pageable, PagedResourcesAssembler assembler) {
        Page<CartDto> cartsDto = cartFacade.getCustomerCarts(id, pageable);

        Page<CartResponse> response = cartMapper.mapToCartResponsePage(cartsDto);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "{id}/carts/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCustomerCart(@PathVariable Long id, @PathVariable String uniqueId) {
        CartDto cartDto = cartFacade.getCustomerCartByUniqueId(id, uniqueId);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CustomerController.class).slash(id).slash("carts").slash(cartResponse.getUniqueId()).withSelfRel();

        return new Resource<>(cartResponse, link);
    }
}
