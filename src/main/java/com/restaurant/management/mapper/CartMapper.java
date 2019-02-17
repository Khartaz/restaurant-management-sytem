package com.restaurant.management.mapper;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.dto.CartDto;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public Cart mapToCart(final CartDto cartDto) {
        return new Cart(
                cartDto.getId(),
                cartDto.getOpen(),
                cartDto.getCustomer(),
                cartDto.getLineItems()
        );
    }

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getOpen(),
                cart.getCustomer(),
                cart.getLineItems()
        );
    }
}
