package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private IngredientMapper ingredientMapper;

    @Autowired
    public void setIngredientMapper(IngredientMapper ingredientMapper) {
        this.ingredientMapper = ingredientMapper;
    }

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

    public ProductArchive mapToProductArchive(final Product product) {
        return new ProductArchive(
                product.getUniqueId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductArchive mapToProductArchive(final ProductDto productDto) {
        return new ProductArchive(
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getCreatedAt(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductDto mapToProductDto(Product product) {
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

    public ProductDto mapToProductDto(final ProductArchive productArchive) {
        return new ProductDto(
                productArchive.getId(),
                productArchive.getUniqueId(),
                productArchive.getName(),
                productArchive.getCategory(),
                productArchive.getPrice(),
                productArchive.getCreatedAt(),
                productArchive.getIngredients().stream()
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
                productDto.getCreatedAt(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientResponse(v))
                        .collect(Collectors.toList())
        );
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> products) {
        return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> mapToProductResponseList(final List<ProductDto> products) {
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public List<Product> mapToProductList(final List<ProductDto> products) {
        return products.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }
}
