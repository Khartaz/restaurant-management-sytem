package com.restaurant.management.mapper;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.web.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getId(),
                cartDto.getCartNumber(),
                cartDto.getOpen(),
                customerMapper.mapToCustomer(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItem(v))
                        .collect(Collectors.toList())
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getCartNumber(),
                cart.getOpen(),
                customerMapper.mapToCustomerDto(cart.getCustomer()),
                cart.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemDto(v)).
                        collect(Collectors.toList())
        );
    }

    public CartResponse mapToCartResponse(final CartDto cartDto) {
        return new CartResponse(
                cartDto.getCartNumber(),
                cartDto.getOpen(),
                customerMapper.mapToCustomerResponse(cartDto.getCustomer()),
                cartDto.getLineItems().stream()
                        .map(v -> lineItemMapper.mapToLineItemResponse(v))
                        .collect(Collectors.toList())
        );
    }
}
