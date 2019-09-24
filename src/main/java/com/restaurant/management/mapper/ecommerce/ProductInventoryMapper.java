package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.ProductInventory;
import com.restaurant.management.domain.ecommerce.dto.ProductInventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductInventoryMapper {

    public ProductInventory mapToProductInventory(final ProductInventoryDTO productInventoryDTO) {
        return new ProductInventory(
                productInventoryDTO.getCreatedAt(),
                productInventoryDTO.getUpdatedAt(),
                productInventoryDTO.getCreatedByUserId(),
                productInventoryDTO.getUpdatedByUserId(),
                productInventoryDTO.getId(),
                productInventoryDTO.getSku(),
                productInventoryDTO.getQuantity()
        );
    }

    public ProductInventoryDTO mapToProductInventoryDTO(final ProductInventory productInventory) {
        return new ProductInventoryDTO(
                productInventory.getCreatedAt(),
                productInventory.getUpdatedAt(),
                productInventory.getCreatedByUserId(),
                productInventory.getUpdatedByUserId(),
                productInventory.getId(),
                productInventory.getSku(),
                productInventory.getQuantity()
        );
    }
}
