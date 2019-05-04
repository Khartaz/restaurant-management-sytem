package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.domain.dto.ProductHistoryDto;
import com.restaurant.management.domain.history.ProductHistory;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.ProductHistoryService;
import com.restaurant.management.service.ProductService;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ProductFacade {
    private ProductService productService;
    private ProductMapper productMapper;
    private ProductHistoryService productHistoryService;

    @Autowired
    public ProductFacade(ProductService productService,
                         ProductMapper productMapper,
                         ProductHistoryService productHistoryService) {
        this.productService = productService;
        this.productMapper = productMapper;
        this.productHistoryService = productHistoryService;
    }

    public ProductDto registerProduct(RegisterProductRequest request) {
        Product product = productService.registerProduct(request);

        return productMapper.mapToProductDto(product);
    }

    public ProductDto updateProduct(ProductRequest request) {
        Product product = productService.updateProduct(request);

        return productMapper.mapToProductDto(product);
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        return productMapper.mapToProductDtoPage(products);
    }

    public ProductDto getProductByUniqueId(String uniqueId) {
        Product product = productService.getProductByUniqueId(uniqueId);

        return productMapper.mapToProductDto(product);
    }

    public ApiResponse deleteByUniqueId(String uniqueId) {

        return productService.deleteByUniqueId(uniqueId);
    }

    public List<ProductDto> getAllByName(String name, Pageable pageable) {
        List<Product> products = productService.getAllByName(name, pageable);

        return productMapper.mapToProductDtoList(products);
    }

    public List<ProductHistoryDto> getProductHistory(Long productId) {
        List<ProductHistory> productHistory = productHistoryService.productRevisions(productId);

        return productMapper.mapToProductHistoryDtoList(productHistory);
    }
}
