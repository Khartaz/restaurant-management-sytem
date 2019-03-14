package com.restaurant.management.mapper;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

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
                cartDto.getUniqueId(),
                cartDto.getOpen(),
                customerMapper.mapToCustomer(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToSessionLineItem(v))
                        .collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(final SessionCart sessionCart) {
        return new CartDto(
                sessionCart.getId(),
                sessionCart.getUniqueId(),
                sessionCart.getOpen(),
                customerMapper.mapToCustomerDto(sessionCart.getCustomer()),
                sessionCart.getSessionLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemDto(v)).
                        collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUniqueId(),
                cart.getOpen(),
                customerMapper.mapToCustomerDto(cart.getCustomer()),
                cart.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemDto(v)).
                        collect(Collectors.toList())
        );
    }

    public CartResponse mapToCartResponse(final CartDto cartDto) {
        return new CartResponse(
                cartDto.getId(),
                cartDto.getUniqueId(),
                cartDto.getOpen(),
                customerMapper.mapToCustomerResponse(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemResponse(v))
                        .collect(Collectors.toList())
        );
    }

    public Cart mapToCart(final SessionCart sessionCart) {
        return new Cart(
                sessionCart.getUniqueId(),
                sessionCart.getOpen(),
                customerMapper.mapToCustomerArchive(sessionCart.getCustomer()),
                sessionCart.getSessionLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemArchive(v))
                        .collect(Collectors.toList())

        );
    }

    public List<CartDto> mapToCartDtoList(final List<Cart> carts) {
        return carts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }

    public List<CartDto> mapToSessionCartDtoList(final List<SessionCart> sessionCarts) {
        return sessionCarts.stream()
                .map(this::mapToCartDto)
                .collect(Collectors.toList());
    }

    public List<CartResponse> mapToCartResponseList(final List<CartDto> carts) {
        return carts.stream()
                .map(this::mapToCartResponse)
                .collect(Collectors.toList());
    }

    public List<SessionCart> mapToSessionCartList(final List<CartDto> carts) {
        return carts.stream()
                .map(this::mapToSessionCart)
                .collect(Collectors.toList());
    }

}
