package com.restaurant.management.mapper;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class CartMapper {

    private CustomerMapper customerMapper;
    private LineItemMapper lineItemMapper;

    @Autowired
    public CartMapper(CustomerMapper customerMapper, LineItemMapper lineItemMapper) {
        this.customerMapper = customerMapper;
        this.lineItemMapper = lineItemMapper;
    }

    public SessionCart mapToSessionCart(final CartDto cartDto) {
        return new SessionCart(
                cartDto.getId(),
                cartDto.isOpen(),
                cartDto.getTotalPrice(),
                customerMapper.mapToCustomer(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToSessionLineItem(v))
                        .collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(SessionCart sessionCart) {
        return new CartDto(
                sessionCart.getId(),
                sessionCart.isOpen(),
                sessionCart.getTotalPrice(),
                customerMapper.mapToCustomerDto(sessionCart.getCustomer()),
                sessionCart.getSessionLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemDto(v)).
                        collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.isOpen(),
                cart.getTotalPrice(),
                customerMapper.mapToCustomerDto(cart.getCustomer()),
                cart.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemDto(v)).
                        collect(Collectors.toList())
        );
    }

    public CartResponse mapToCartResponse(final CartDto cartDto) {
        return new CartResponse(
                cartDto.getId(),
                cartDto.isOpen(),
                cartDto.getTotalPrice(),
                customerMapper.mapToCustomerResponse(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemResponse(v))
                        .collect(Collectors.toList())
        );
    }

    public Cart mapToCart(SessionCart sessionCart) {
        return new Cart(
                sessionCart.isOpen(),
                sessionCart.getTotalPrice(),
                customerMapper.mapToCustomerArchive(sessionCart.getCustomer()),
                sessionCart.getSessionLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public Cart mapToCart(CartDto cartDto) {
        return new Cart(
                cartDto.isOpen(),
                cartDto.getTotalPrice(),
                customerMapper.mapToCustomerArchive(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> carts) {
        return carts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }

    public List<CartDto> mapSessionCartToCartDtoList(final List<SessionCart> sessionCarts) {
        return sessionCarts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }

    public Page<CartDto> mapToCartDtoPage(final Page<Cart> carts) {
        return carts.map(this::mapToCartDto);
    }

    public List<CartResponse> mapToCartResponseList(final List<CartDto> carts) {
        return carts.stream()
                .map(this::mapToCartResponse)
                .collect(Collectors.toList());
    }

    public Page<CartResponse> mapToCartResponsePage(final Page<CartDto> carts) {
        return carts.map(this::mapToCartResponse);
    }

    public List<SessionCart> mapToSessionCartList(final List<CartDto> carts) {
        return carts.stream()
                .map(this::mapToSessionCart)
                .collect(Collectors.toList());
    }

    public Page<SessionCart> mapToSessionCartPage(final Page<CartDto> carts) {
        return carts.map(this::mapToSessionCart);
    }

    public Page<CartDto> mapToCartDto(final Page<SessionCart> sessionCarts) {
        return sessionCarts.map(this::mapToCartDto);
    }

}
