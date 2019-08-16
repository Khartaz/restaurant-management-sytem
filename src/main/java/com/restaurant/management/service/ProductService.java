package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.domain.ecommerce.dto.ProductFormDTO;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Product registerProduct(@CurrentUser UserPrincipal currentUser, ProductFormDTO request);

    Product updateProduct(ProductFormDTO productFormDTO, @CurrentUser UserPrincipal currentUser);

    Product getProductById(Long productId, @CurrentUser UserPrincipal currentUser);

    Page<Product> getAllByCompany(Pageable pageable, @CurrentUser UserPrincipal currentUser);

    ApiResponse deleteById(Long id, @CurrentUser UserPrincipal currentUser);

    ApiResponse deleteAllByIds(Long[] productsIds, @CurrentUser UserPrincipal currentUser);

}
