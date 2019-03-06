package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.exception.cart.CartMessages;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.request.order.OrderRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/carts")
public class CartController {

    private CartService cartService;
    private CartMapper cartMapper;

    @Autowired
    public CartController(CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @PutMapping(value = "/addToCart",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> addToCart(@RequestBody OrderRequest orderRequest) {
        CartDto cartDto = cartService.addToCart(orderRequest.getPhoneNumber(), orderRequest.getProductName(), orderRequest.getQuantity());

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<?> deleteCart(@PathVariable String uniqueId) {
        cartService.deleteCart(uniqueId);
        return ResponseEntity.ok().body(new ApiResponse(true, CartMessages.CART_DELETED.getErrorMessage()));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<CartResponse> showCarts() {
        List<CartDto> cartDtos = cartService.getAllCarts();

        List<CartResponse> responses = cartMapper.mapToCartResponseList(cartDtos);

        Link link = linkTo(CartController.class).withSelfRel();

        return new Resources<>(responses, link);
    }

    @GetMapping(value = "/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCart(@PathVariable String uniqueId) {
        CartDto cartDto = cartService.getCartByUniqueId(uniqueId);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
