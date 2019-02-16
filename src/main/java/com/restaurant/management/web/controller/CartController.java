package com.restaurant.management.web.controller;

import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.service.CartService;
import com.restaurant.management.web.request.order.OrderRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public @ResponseBody
    ResponseEntity<?> addProduct(@RequestBody OrderRequest orderRequest) {
        cartService.addToCart(orderRequest.getPhoneNumber(), orderRequest.getProductName(), orderRequest.getQuantity());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(orderRequest.getProductName()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, UserMessages.REGISTER_SUCCESS.getErrorMessage()));
    }

}
