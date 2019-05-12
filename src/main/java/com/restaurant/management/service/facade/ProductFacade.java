package com.restaurant.management.service.facade;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.domain.dto.ProductHistoryDto;
import com.restaurant.management.domain.history.ProductHistory;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.impl.ProductHistoryService;
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

    public ProductDto registerProduct(@CurrentUser UserPrincipal currentUser, RegisterProductRequest request) {
        Product product = productService.registerProduct(currentUser, request);

        return productMapper.mapToProductDto(product);
    }

    public ProductDto updateProduct(ProductRequest request, @CurrentUser UserPrincipal currentUser) {
        Product product = productService.updateProduct(request, currentUser);

        return productMapper.mapToProductDto(product);
    }

    public ProductDto getProductById(Long id, @CurrentUser UserPrincipal currentUser) {
        Product product = productService.getProductById(id, currentUser);

        return productMapper.mapToProductDto(product);
    }

    public ApiResponse deleteById(Long id, @CurrentUser UserPrincipal currentUser) {

        return productService.deleteById(id, currentUser);
    }

    public List<ProductHistoryDto> getProductHistory(Long productId, @CurrentUser UserPrincipal currentUser) {
        List<ProductHistory> productHistory = productHistoryService.productRevisions(productId, currentUser);

        return productMapper.mapToProductHistoryDtoList(productHistory);
    }

    public Page<ProductDto> getAllByRestaurant(Pageable pageable, @CurrentUser UserPrincipal currentUser) {
        Page<Product> products = productService.getAllByRestaurant(pageable, currentUser);

        return productMapper.mapToProductDtoPage(products);
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        return productMapper.mapToProductDtoPage(products);
    }

    public ProductDto getProductById(Long id) {
        Product product = productService.getProductById(id);

        return productMapper.mapToProductDto(product);
    }
}
