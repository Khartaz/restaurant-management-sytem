package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.CartFacade;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.CartResponse;
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
@RequestMapping("/api/carts")
public class CartController {
    private CartFacade cartFacade;
    private CartMapper cartMapper;

    @Autowired
    public CartController(CartFacade cartFacade,
                          CartMapper cartMapper) {
        this.cartFacade = cartFacade;
        this.cartMapper = cartMapper;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CartResponse>> showCarts(@CurrentUser UserPrincipal currentUser,
                                                           Pageable pageable,
                                                           PagedResourcesAssembler assembler) {
        Page<CartDto> cartsDto = cartFacade.getAllCarts(currentUser, pageable);

        Page<CartResponse> response = cartMapper.mapToCartResponsePage(cartsDto);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{cartId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCart(@CurrentUser UserPrincipal currentUser,
                                    @PathVariable Long cartId) {
        CartDto cartDto = cartFacade.getCartById(currentUser, cartId);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<CartResponse>> showSessionCarts(@CurrentUser UserPrincipal currentUser,
                                                                  Pageable pageable,
                                                                  PagedResourcesAssembler assembler) {
        Page<CartDto> cartsDto = cartFacade.getSessionCarts(currentUser, pageable);

        Page<CartResponse> response = cartMapper.mapToCartResponsePage(cartsDto);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/session/{cartId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showSessionCart(@CurrentUser UserPrincipal currentUser,
                                           @PathVariable Long cartId) {
        CartDto cartDto = cartFacade.getSessionCartById(currentUser, cartId);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/session/{cartId}")
    public ResponseEntity<?> deleteSessionCart(@CurrentUser UserPrincipal currentUser,
                                               @PathVariable Long cartId) {
        ApiResponse response = cartFacade.deleteSessionCart(currentUser, cartId);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/customer/{customerId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> registerCustomerCart(@CurrentUser UserPrincipal currentUser,
                                                @PathVariable Long customerId) {
        CartDto cartDto = cartFacade.openSessionCart(currentUser, customerId);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash("customer").slash(cartResponse.getCustomer().getId()).withSelfRel();

        return new Resource<>(cartResponse, link);
    }

    @PutMapping(value = "/session/{customerId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> addToSessionCart(@CurrentUser UserPrincipal currentUser,
                                            @PathVariable Long customerId,
                                            @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.addToCart(currentUser, customerId, request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash("session").slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/session/{customerId}/product", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> removeProductFromCart(@CurrentUser UserPrincipal currentUser,
                                                 @PathVariable Long customerId,
                                                 @Valid @RequestBody RemoveProductRequest request) {
        CartDto cartDto = cartFacade.removeProductFromCart(currentUser, customerId, request);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash("session").slash(cartResponse.getId()).slash("product").withSelfRel();

        return new Resource<>(cartResponse, link);
    }

    @PutMapping(value = "/session/{customerId}/product", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> updateProductQuantity(@CurrentUser UserPrincipal currentUser,
                                                 @PathVariable Long customerId,
                                                 @RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.updateProductQuantity(currentUser, customerId, request);

        CartResponse cartResponse = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash("session").slash(cartResponse.getCustomer().getId()).slash("product").withSelfRel();

        return new Resource<>(cartResponse, link);
    }
}
