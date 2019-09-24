package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.ProductOrderedShippingDetails;
import com.restaurant.management.domain.ecommerce.ProductShippingDetails;
import com.restaurant.management.domain.ecommerce.dto.ProductShippingDetailsDTO;
import org.springframework.stereotype.Component;

@Component
public final class ProductShippingDetailsMapper {

    public ProductShippingDetails mapToProductShippingDetails(final ProductShippingDetailsDTO productShippingDetailsDTO) {
        return new ProductShippingDetails(
                productShippingDetailsDTO.getCreatedAt(),
                productShippingDetailsDTO.getUpdatedAt(),
                productShippingDetailsDTO.getCreatedByUserId(),
                productShippingDetailsDTO.getUpdatedByUserId(),
                productShippingDetailsDTO.getId(),
                productShippingDetailsDTO.getWidth(),
                productShippingDetailsDTO.getHeight(),
                productShippingDetailsDTO.getDepth(),
                productShippingDetailsDTO.getWeight(),
                productShippingDetailsDTO.getExtraShippingFee()
        );
    }

    public ProductShippingDetailsDTO mapToProductShippingDetailsDTO(final ProductShippingDetails productShippingDetails) {
        return new ProductShippingDetailsDTO(
                productShippingDetails.getCreatedAt(),
                productShippingDetails.getUpdatedAt(),
                productShippingDetails.getCreatedByUserId(),
                productShippingDetails.getUpdatedByUserId(),
                productShippingDetails.getId(),
                productShippingDetails.getWidth(),
                productShippingDetails.getHeight(),
                productShippingDetails.getDepth(),
                productShippingDetails.getWeight(),
                productShippingDetails.getExtraShippingFee()
        );
    }

    public ProductShippingDetailsDTO mapToProductShippingDetailsDTO(final ProductOrderedShippingDetails productOrderedShippingDetails) {
        return new ProductShippingDetailsDTO(
                productOrderedShippingDetails.getCreatedAt(),
                productOrderedShippingDetails.getUpdatedAt(),
                productOrderedShippingDetails.getCreatedByUserId(),
                productOrderedShippingDetails.getUpdatedByUserId(),
                productOrderedShippingDetails.getId(),
                productOrderedShippingDetails.getWidth(),
                productOrderedShippingDetails.getHeight(),
                productOrderedShippingDetails.getDepth(),
                productOrderedShippingDetails.getWeight(),
                productOrderedShippingDetails.getExtraShippingFee()
        );
    }

    public ProductOrderedShippingDetails mapToProductOrderedShippingDetails(final ProductShippingDetails productShippingDetails) {
        return new ProductOrderedShippingDetails(
                productShippingDetails.getCreatedAt(),
                productShippingDetails.getUpdatedAt(),
                productShippingDetails.getCreatedByUserId(),
                productShippingDetails.getUpdatedByUserId(),
                productShippingDetails.getId(),
                productShippingDetails.getWidth(),
                productShippingDetails.getHeight(),
                productShippingDetails.getDepth(),
                productShippingDetails.getWeight(),
                productShippingDetails.getExtraShippingFee()
        );
    }

    public ProductOrderedShippingDetails mapToProductOrderedShippingDetails(final ProductShippingDetailsDTO productShippingDetailsDTO) {
        return new ProductOrderedShippingDetails(
                productShippingDetailsDTO.getCreatedAt(),
                productShippingDetailsDTO.getUpdatedAt(),
                productShippingDetailsDTO.getCreatedByUserId(),
                productShippingDetailsDTO.getUpdatedByUserId(),
                productShippingDetailsDTO.getId(),
                productShippingDetailsDTO.getWidth(),
                productShippingDetailsDTO.getHeight(),
                productShippingDetailsDTO.getDepth(),
                productShippingDetailsDTO.getWeight(),
                productShippingDetailsDTO.getExtraShippingFee()
        );
    }
}
