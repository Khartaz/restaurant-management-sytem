package com.restaurant.management.mapper;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.archive.ProductArchive;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.domain.dto.ProductHistoryDto;
import com.restaurant.management.domain.dto.RevisionTypeDto;
import com.restaurant.management.domain.history.ProductHistory;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("Duplicates")
public final class ProductMapper {

    private IngredientMapper ingredientMapper;
    private RevisionTypeMapper revisionTypeMapper;

    @Autowired
    public ProductMapper(IngredientMapper ingredientMapper,
                         RevisionTypeMapper revisionTypeMapper) {
        this.ingredientMapper = ingredientMapper;
        this.revisionTypeMapper = revisionTypeMapper;
    }

    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredient(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductArchive mapToProductArchive(final Product product) {
        return new ProductArchive(
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCreatedByUserId(),
                product.getUpdatedByUserId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductArchive mapToProductArchive(final ProductDto productDto) {
        return new ProductArchive(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientArchive(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCreatedByUserId(),
                product.getUpdatedByUserId(),
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientDto(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductDto mapToProductDto(final ProductArchive productArchive) {
        return new ProductDto(
                productArchive.getCreatedAt(),
                productArchive.getUpdatedAt(),
                productArchive.getCreatedByUserId(),
                productArchive.getUpdatedByUserId(),
                productArchive.getId(),
                productArchive.getName(),
                productArchive.getCategory(),
                productArchive.getPrice(),
                productArchive.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientDto(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductResponse mapToProductResponse(final ProductDto productDto) {
        return new ProductResponse(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                productDto.getIngredients().stream()
                        .map(v -> ingredientMapper.mapToIngredientResponse(v))
                        .collect(Collectors.toList())
        );
    }

    public ProductHistoryDto mapToProductHistoryDto(ProductHistory productHistory) {
        ProductDto productDto = mapToProductDto(productHistory.getProduct());
        Long revision = productHistory.getRevisionType().getRepresentation().longValue();
        RevisionTypeDto revisionTypeDto = revisionTypeMapper.mapToRevisionTypeDto(productHistory.getRevisionType());

        return new ProductHistoryDto(productDto, revision, revisionTypeDto);
    }

    public List<ProductHistoryDto> mapToProductHistoryDtoList(final List<ProductHistory> productHistory) {
        return productHistory.stream()
                .map(this::mapToProductHistoryDto)
                .collect(Collectors.toList());
    }

    public Page<ProductHistoryDto> mapToProductHistoryPage(final Page<ProductHistory> productHistory) {
        return productHistory.map(this::mapToProductHistoryDto);
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> products) {
        return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }


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
