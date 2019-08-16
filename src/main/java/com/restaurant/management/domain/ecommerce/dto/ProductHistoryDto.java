package com.restaurant.management.domain.ecommerce.dto;

public final class ProductHistoryDto {

    private ProductDTO productDto;
    private Long revision;
    private RevisionTypeDto revisionTypeDto;

    public ProductHistoryDto() {
    }

    public ProductHistoryDto(ProductDTO productDto, Long revision, RevisionTypeDto revisionTypeDto) {
        this.productDto = productDto;
        this.revision = revision;
        this.revisionTypeDto = revisionTypeDto;
    }

    public ProductDTO getProductDto() {
        return productDto;
    }

    public Long getRevision() {
        return revision;
    }

    public RevisionTypeDto getRevisionTypeDto() {
        return revisionTypeDto;
    }
}
