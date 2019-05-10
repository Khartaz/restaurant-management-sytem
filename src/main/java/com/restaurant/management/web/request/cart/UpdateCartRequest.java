package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotNull;

public final class UpdateCartRequest {

    @NotNull(message = "product id cannot be null")
    private Long productId;

    @NotNull(message = "quantity cannot be null")
    private Integer quantity;

    public UpdateCartRequest() {
    }

    public UpdateCartRequest(Long productId,
                             Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
