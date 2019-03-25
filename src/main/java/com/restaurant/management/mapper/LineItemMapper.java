package com.restaurant.management.mapper;

import com.restaurant.management.domain.archive.LineItemArchive;
import com.restaurant.management.domain.SessionLineItem;
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

    public LineItemArchive mapToLineItemArchive(final SessionLineItem sessionLineItem) {
        return new LineItemArchive(
                sessionLineItem.getQuantity(),
                sessionLineItem.getPrice(),
                productMapper.mapToProductArchive(sessionLineItem.getProduct())
        );
    }

    public LineItemArchive mapToLineItemArchive(final LineItemDto lineItemDto) {
        return new LineItemArchive(
                lineItemDto.getQuantity(),
                lineItemDto.getPrice(),
                productMapper.mapToProductArchive(lineItemDto.getProductDto())
        );
    }

    public SessionLineItem mapToSessionLineItem(final LineItemDto lineItemDto) {
        return new SessionLineItem(
                lineItemDto.getId(),
                lineItemDto.getQuantity(),
                lineItemDto.getPrice(),
                productMapper.mapToProduct(lineItemDto.getProductDto())
        );
    }

    public LineItemDto mapToLineItemDto(SessionLineItem sessionLineItem) {
        return new LineItemDto(
                sessionLineItem.getId(),
                sessionLineItem.getQuantity(),
                sessionLineItem.getPrice(),
                productMapper.mapToProductDto(sessionLineItem.getProduct())
        );
    }

    public LineItemDto mapToLineItemDto(final LineItemArchive lineItemArchive) {
        return new LineItemDto(
                lineItemArchive.getId(),
                lineItemArchive.getQuantity(),
                lineItemArchive.getPrice(),
                productMapper.mapToProductDto(lineItemArchive.getProduct())
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
