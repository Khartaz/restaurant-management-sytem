package com.restaurant.management.web.request.product;

public final class ProductRequest {
    private Long id;
    private String name;
    private Double priceTaxIncl;
    private String description;
    private String sku;
    private Double quantity;
    private Double width;
    private Double height;
    private Double depth;
    private Double weight;
    private Double extraShippingFee;

    public ProductRequest() {
    }

    public ProductRequest(Long id, String name, Double priceTaxIncl,
                          String description, String sku, Double quantity,
                          Double width, Double height, Double depth,
                          Double weight, Double extraShippingFee) {
        this.id = id;
        this.name = name;
        this.priceTaxIncl = priceTaxIncl;
        this.description = description;
        this.sku = sku;
        this.quantity = quantity;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.weight = weight;
        this.extraShippingFee = extraShippingFee;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPriceTaxIncl() {
        return priceTaxIncl;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }

    public Double getDepth() {
        return depth;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getExtraShippingFee() {
        return extraShippingFee;
    }
}
