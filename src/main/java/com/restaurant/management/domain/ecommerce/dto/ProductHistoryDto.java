package com.restaurant.management.domain.ecommerce.dto;

public final class ProductHistoryDto {

    private ProductDto productDto;
    private Long revision;
    private RevisionTypeDto revisionTypeDto;

    public ProductHistoryDto() {
    }

    public ProductHistoryDto(ProductDto productDto, Long revision, RevisionTypeDto revisionTypeDto) {
        this.productDto = productDto;
        this.revision = revision;
        this.revisionTypeDto = revisionTypeDto;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public Long getRevision() {
        return revision;
    }

    public RevisionTypeDto getRevisionTypeDto() {
        return revisionTypeDto;
    }
}
