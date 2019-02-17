package com.restaurant.management.mapper;

import com.restaurant.management.domain.LineItem;
import com.restaurant.management.domain.dto.LineItemDto;
import com.restaurant.management.web.response.LineItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LineItemMapper {

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public LineItem mapToLineItem(final LineItemDto lineItemDto) {
        return new LineItem(
                productMapper.mapToProduct(lineItemDto.getProductDto()),
                lineItemDto.getQuantity(),
                lineItemDto.getPrice()
        );
    }

    public LineItemDto mapToLineItemDto(final LineItem lineItem) {
        return new LineItemDto(
                productMapper.mapToProductDto(lineItem.getProduct()),
                lineItem.getQuantity(),
                lineItem.getPrice()
        );
    }

    public LineItemResponse mapToLineItemResponse(final LineItemDto lineItemDto) {
        return new LineItemResponse(
                productMapper.mapToProductResponse(lineItemDto.getProductDto()),
                lineItemDto.getQuantity(),
                lineItemDto.getPrice()
        );
    }
}
