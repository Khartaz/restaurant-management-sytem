package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.domain.ecommerce.ProductOrdered;
import com.restaurant.management.domain.ecommerce.dto.ProductDto;
import com.restaurant.management.domain.ecommerce.dto.ProductHistoryDto;
import com.restaurant.management.domain.ecommerce.dto.RevisionTypeDto;
import com.restaurant.management.domain.ecommerce.history.ProductHistory;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("Duplicates")
public final class ProductMapper {

    private RevisionTypeMapper revisionTypeMapper;

    @Autowired
    public ProductMapper(RevisionTypeMapper revisionTypeMapper) {
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
                productDto.getPrice()
        );
    }

    public ProductOrdered mapToProductOrdered(final Product product) {
        return new ProductOrdered(
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCreatedByUserId(),
                product.getUpdatedByUserId(),
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice()
        );
    }

    public ProductOrdered mapToProductOrdered(final ProductDto productDto) {
        return new ProductOrdered(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice()
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
                product.getPrice()
        );
    }

    public ProductDto mapToProductDto(final ProductOrdered productOrdered) {
        return new ProductDto(
                productOrdered.getCreatedAt(),
                productOrdered.getUpdatedAt(),
                productOrdered.getCreatedByUserId(),
                productOrdered.getUpdatedByUserId(),
                productOrdered.getId(),
                productOrdered.getName(),
                productOrdered.getCategory(),
                productOrdered.getPrice()
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
                productDto.getPrice()
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
