package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.LineItemOrdered;
import com.restaurant.management.domain.ecommerce.LineItem;
import com.restaurant.management.domain.ecommerce.dto.LineItemDto;
import com.restaurant.management.web.response.LineItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class LineItemMapper {

    private ProductMapper productMapper;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public LineItemOrdered mapToLineItemOrdered(final LineItem lineItem) {
        return new LineItemOrdered(
                lineItem.getQuantity(),
                lineItem.getPrice(),
                productMapper.mapToProductOrdered(lineItem.getProduct())
        );
    }

    public LineItemOrdered mapToLineItemOrdered(final LineItemDto lineItemDto) {
        return new LineItemOrdered(
                lineItemDto.getQuantity(),
                lineItemDto.getPrice(),
                productMapper.mapToProductOrdered(lineItemDto.getProductDto())
        );
    }

    public LineItem mapToSessionLineItem(final LineItemDto lineItemDto) {
        return new LineItem(
                lineItemDto.getId(),
                lineItemDto.getQuantity(),
                lineItemDto.getPrice(),
                productMapper.mapToProduct(lineItemDto.getProductDto())
        );
    }

    public LineItemDto mapToLineItemDto(LineItem lineItem) {
        return new LineItemDto(
                lineItem.getId(),
                lineItem.getQuantity(),
                lineItem.getPrice(),
                productMapper.mapToProductDto(lineItem.getProduct())
        );
    }

    public LineItemDto mapToLineItemDto(final LineItemOrdered lineItemOrdered) {
        return new LineItemDto(
                lineItemOrdered.getId(),
                lineItemOrdered.getQuantity(),
                lineItemOrdered.getPrice(),
                productMapper.mapToProductDto(lineItemOrdered.getProductOrdered())
        );
    }

    public LineItemResponse mapToLineItemResponse(final LineItemDto lineItemDto) {
        return new LineItemResponse(
                lineItemDto.getId(),
                lineItemDto.getQuantity(),
                lineItemDto.getPrice(),
                productMapper.mapToProductResponse(lineItemDto.getProductDto())
        );
    }

}
