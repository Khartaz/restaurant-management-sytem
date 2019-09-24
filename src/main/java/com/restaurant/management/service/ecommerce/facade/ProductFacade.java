package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.domain.ecommerce.dto.ProductDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductFormDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductHistoryDto;
import com.restaurant.management.domain.ecommerce.history.ProductHistory;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.ProductHistoryService;
import com.restaurant.management.service.ecommerce.ProductService;
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

    public ProductFormDTO registerProduct(@CurrentUser UserPrincipal currentUser, ProductFormDTO request) {
        Product product = productService.registerProduct(currentUser, request);

        return productMapper.mapToProductFormDTO(product);
    }

    public ProductFormDTO updateProduct(@CurrentUser UserPrincipal currentUser, ProductFormDTO request) {
        Product product = productService.updateProduct(request, currentUser);

        return productMapper.mapToProductFormDTO(product);
    }

    public Page<ProductFormDTO> getAllByRestaurant(Pageable pageable, @CurrentUser UserPrincipal currentUser) {
        Page<Product> products = productService.getAllByCompany(pageable, currentUser);

        return productMapper.mapToProductFormDTOPage(products);
    }

    public ProductDTO getRestaurantProductById(Long id, @CurrentUser UserPrincipal currentUser) {
        Product product = productService.getProductById(id, currentUser);

        return productMapper.mapToProductDto(product);
    }

    public ApiResponse deleteById(Long id, @CurrentUser UserPrincipal currentUser) {

        return productService.deleteById(id, currentUser);
    }

    public ApiResponse deleteAllByIds(Long[] productsIds, @CurrentUser UserPrincipal currentUser) {
        return productService.deleteAllByIds(productsIds, currentUser);
    }

    public List<ProductHistoryDto> getProductHistory(Long productId, @CurrentUser UserPrincipal currentUser) {
        List<ProductHistory> productHistory = productHistoryService.productRevisions(productId, currentUser);

        return productMapper.mapToProductHistoryDtoList(productHistory);
    }

}
