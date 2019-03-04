package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProductMapper {

    @Autowired
    private IngredientMapper ingredientMapper;

    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getCreatedAt(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredient(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getUniqueId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientDto(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductResponse mapToProductResponse(final ProductDto productDto) {
        return new ProductResponse(
                productDto.getId(),
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientResponse(v))
                        .collect(Collectors.toList())
        );
    }
}
