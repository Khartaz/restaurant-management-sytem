package com.restaurant.management.web.request.cart;

import javax.validation.constraints.NotNull;

public final class RemoveProductRequest {

    @NotNull(message = "product id cannot be null")
    private Long productId;

    public RemoveProductRequest() {
    }

    public RemoveProductRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
