package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.CartOrdered;
import com.restaurant.management.domain.ecommerce.Cart;
import com.restaurant.management.domain.ecommerce.dto.CartDto;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

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

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getId(),
                cartDto.isOpen(),
                cartDto.getTotalPrice(),
                customerMapper.mapToCustomer(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToSessionLineItem(v))
                        .collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(Cart cart) {
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

    public CartDto mapToCartDto(final CartOrdered cartOrdered) {
        return new CartDto(
                cartOrdered.getId(),
                cartOrdered.isOpen(),
                cartOrdered.getTotalPrice(),
                customerMapper.mapToCustomerDto(cartOrdered.getCustomerOrdered()),
                cartOrdered.getLineItemsOrdered().stream()
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

    public CartOrdered mapToCartOrdered(Cart cart) {
        return new CartOrdered(
                cart.isOpen(),
                cart.getTotalPrice(),
                customerMapper.mapToCustomerOrdered(cart.getCustomer()),
                cart.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemOrdered(v))
                        .collect(Collectors.toList())
        );
    }

    public CartOrdered mapToCartOrdered(CartDto cartDto) {
        return new CartOrdered(
                cartDto.isOpen(),
                cartDto.getTotalPrice(),
                customerMapper.mapToCustomerOrdered(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemOrdered(v))
                        .collect(Collectors.toList())
        );
    }

    public Page<CartDto> mapToCartDtoPage(final Page<CartOrdered> carts) {
        return carts.map(this::mapToCartDto);
    }

    public Page<CartResponse> mapToCartResponsePage(final Page<CartDto> carts) {
        return carts.map(this::mapToCartResponse);
    }

    public Page<CartDto> mapToCartDto(final Page<Cart> sessionCarts) {
        return sessionCarts.map(this::mapToCartDto);
    }

}
