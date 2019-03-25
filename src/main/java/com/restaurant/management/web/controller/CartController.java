package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.service.facade.CartFacade;
import com.restaurant.management.web.request.cart.RegisterCartRequest;
import com.restaurant.management.web.request.cart.RemoveProductRequest;
import com.restaurant.management.web.request.cart.UpdateCartRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    Resources<CartResponse> showCarts() {
        List<CartDto> cartDtos = cartFacade.getAllCarts();

        List<CartResponse> response = cartMapper.mapToCartResponseList(cartDtos);

        Link link = linkTo(CartController.class).withSelfRel();

        return new Resources<>(response, link);
    }

    @GetMapping(value = "/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showCart(@PathVariable String uniqueId) {
        CartDto cartDto = cartFacade.getCartByUniqueId(uniqueId);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(value = "/session", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<CartResponse> showSessionCarts() {
        List<CartDto> cartsDto = cartFacade.getSessionCarts();

        List<CartResponse> response = cartMapper.mapToCartResponseList(cartsDto);

        Link link = linkTo(CartController.class).withSelfRel();

        return new Resources<>(response, link);
    }

    @GetMapping(value = "/session/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> showSessionCart(@PathVariable String uniqueId) {
        CartDto cartDto = cartFacade.getSessionCartByUniqueId(uniqueId);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/session/{uniqueId}")
    public ResponseEntity<?> deleteSessionCart(@PathVariable String uniqueId) {
        ApiResponse response = cartFacade.deleteSessionCart(uniqueId);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/product",
            produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> removeProductCart(@Valid @RequestBody RemoveProductRequest request) {
        CartDto cartDto = cartFacade.removeProductFromCart(request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> registerCustomerCart(@Valid @RequestBody RegisterCartRequest request) {
        CartDto cartDto = cartFacade.openSessionCart(request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PutMapping(value = "/addToCart",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> addToCart(@RequestBody UpdateCartRequest updateCartRequest) {
        CartDto cartDto = cartFacade.addToCart(updateCartRequest);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @PutMapping(value = "/product",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CartResponse> updateProductQuantity(@RequestBody UpdateCartRequest request) {
        CartDto cartDto = cartFacade.updateProductQuantity(request);

        CartResponse response = cartMapper.mapToCartResponse(cartDto);

        Link link = linkTo(CartController.class).slash(response.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
