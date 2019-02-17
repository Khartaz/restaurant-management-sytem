package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getIngredients(),
                productDto.getCreatedAt()
        );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getIngredients(),
                product.getCreatedAt()
        );
    }
}
