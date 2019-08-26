package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.domain.ecommerce.ProductOrdered;
import com.restaurant.management.domain.ecommerce.dto.ProductDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductFormDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductHistoryDto;
import com.restaurant.management.domain.ecommerce.dto.RevisionTypeDto;
import com.restaurant.management.domain.ecommerce.history.ProductHistory;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("Duplicates")
public final class ProductMapper {

    private RevisionTypeMapper revisionTypeMapper;
    private ProductInventoryMapper productInventoryMapper;
    private ProductShippingDetailsMapper productShippingDetailsMapper;

    @Autowired
    public ProductMapper(RevisionTypeMapper revisionTypeMapper,
                         ProductInventoryMapper productInventoryMapper,
                         ProductShippingDetailsMapper productShippingDetailsMapper) {
        this.revisionTypeMapper = revisionTypeMapper;
        this.productInventoryMapper = productInventoryMapper;
        this.productShippingDetailsMapper = productShippingDetailsMapper;
    }

    public Product mapToProduct(final ProductDTO productDto) {
        return new Product(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getPriceTaxIncl(),
                productDto.getDescription(),
                productShippingDetailsMapper.mapToProductShippingDetails(productDto.getProductShippingDetailsDTO()),
                productInventoryMapper.mapToProductInventory(productDto.getProductInventoryDTO())
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
                product.getPrice(),
                product.getDescription(),
                productShippingDetailsMapper.mapToProductOrderedShippingDetails(product.getProductShippingDetails())
        );
    }

    public ProductOrdered mapToProductOrdered(final ProductDTO productDto) {
        return new ProductOrdered(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getPriceTaxIncl(),
                productDto.getDescription(),
                productShippingDetailsMapper.mapToProductOrderedShippingDetails(productDto.getProductShippingDetailsDTO())
        );
    }

    public ProductDTO mapToProductDto(final Product product) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new ProductDTO(
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCreatedByUserId(),
                product.getUpdatedByUserId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                productShippingDetailsMapper.mapToProductShippingDetailsDTO(product.getProductShippingDetails()),
                productInventoryMapper.mapToProductInventoryDTO(product.getProductInventory())
        );
    }

    public ProductFormDTO mapToProductFormDTO(final Product product) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new ProductFormDTO(
                formatter.format(product.getCreatedAt()),
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getProductInventory().getSku(),
                product.getProductInventory().getQuantity(),
                product.getProductShippingDetails().getWidth(),
                product.getProductShippingDetails().getHeight(),
                product.getProductShippingDetails().getDepth(),
                product.getProductShippingDetails().getWeight(),
                product.getProductShippingDetails().getExtraShippingFee()
        );
    }

    public ProductDTO mapToProductDto(final ProductOrdered productOrdered) {
        return new ProductDTO(
                productOrdered.getCreatedAt(),
                productOrdered.getUpdatedAt(),
                productOrdered.getCreatedByUserId(),
                productOrdered.getUpdatedByUserId(),
                productOrdered.getId(),
                productOrdered.getName(),
                productOrdered.getPrice(),
                productOrdered.getDescription(),
                productShippingDetailsMapper.mapToProductShippingDetailsDTO(productOrdered.getProductOrderedShippingDetails())
        );
    }

    public ProductResponse mapToProductResponse(final ProductDTO productDto) {
        return new ProductResponse(
                productDto.getCreatedAt(),
                productDto.getUpdatedAt(),
                productDto.getCreatedByUserId(),
                productDto.getUpdatedByUserId(),
                productDto.getId(),
                productDto.getName(),
                productDto.getPriceTaxIncl()
        );
    }

    public ProductHistoryDto mapToProductHistoryDto(ProductHistory productHistory) {
        ProductDTO productDto = mapToProductDto(productHistory.getProduct());
        Long revision = productHistory.getRevisionType().getRepresentation().longValue();
        RevisionTypeDto revisionTypeDto = revisionTypeMapper.mapToRevisionTypeDto(productHistory.getRevisionType());

        return new ProductHistoryDto(productDto, revision, revisionTypeDto);
    }

    public List<ProductHistoryDto> mapToProductHistoryDtoList(final List<ProductHistory> productHistory) {
        return productHistory.stream()
                .map(this::mapToProductHistoryDto)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> mapToProductDTOPage(final Page<Product> products) {
        return products.map(this::mapToProductDto);
    }

    public Page<ProductFormDTO> mapToProductFormDTOPage(final Page<Product> products) {
        return products.map(this::mapToProductFormDTO);
    }

}
