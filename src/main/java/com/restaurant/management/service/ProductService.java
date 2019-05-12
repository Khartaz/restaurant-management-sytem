package com.restaurant.management.service;

import com.restaurant.management.domain.Product;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product registerProduct(@CurrentUser UserPrincipal currentUser, RegisterProductRequest request);

    Product updateProduct(ProductRequest productRequest, @CurrentUser UserPrincipal currentUser);

    Product getProductById(Long id, @CurrentUser UserPrincipal currentUser);

    Page<Product> getAllByRestaurant(Pageable pageable, @CurrentUser UserPrincipal currentUser);

    ApiResponse deleteById(Long id, @CurrentUser UserPrincipal currentUser);

    Page<Product> getAllProducts(Pageable pageable);

    Product getProductById(Long id);

    Product updateProduct(ProductRequest productRequest);
}
