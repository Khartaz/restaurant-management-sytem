package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.web.request.product.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToProduct(ProductRequest request) {
        return new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getIngredients()
        );
    }
}
