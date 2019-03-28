package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//    public Page<ProductDto> mapToProductDtoPage(Page<Product> product) {
//        return new ProductDto(
//                product.map(AbstractProduct::getId).get(),
//                product.map(AbstractProduct::getUniqueId).get(),
//                product.map(AbstractProduct::getName).get(),
//                product.map(AbstractProduct::getCategory).get(),
//                product.map(AbstractProduct::getPrice).get(),
//                product.map(AbstractProduct::getCreatedAt).get(),
//                product.map(Product::getIngredients).stream()
//                        .map(v -> ingredientMapper.mapToIngredientDto(v))
//                        .collect(Collectors.toList())
//        );
//    }

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

//    public ProductDto mapToProductDtoList(final Product products) {
//        return products.stream()
//                .map(this::mapToProductDto)
//                .collect(Collectors.toList());
//    }


    public Page<ProductDto> mapToProductDtoPage(final Page<Product> products) {
        return products.map(this::mapToProductDto);
    }

    public List<ProductResponse> mapToProductResponseList(final List<ProductDto> products) {
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Page<ProductResponse> mapToProductResponsePage(final Page<ProductDto> productDtos) {
        return productDtos.map(this::mapToProductResponse);
    }

    public List<Product> mapToProductList(final List<ProductDto> products) {
        return products.stream()
                .map(this::mapToProduct)
                .collect(Collectors.toList());
    }
}
