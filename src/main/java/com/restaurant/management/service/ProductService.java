package com.restaurant.management.service;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.repository.ProductRepository;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.product.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.stream.Stream;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private Utils utils;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          Utils utils) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.utils = utils;
    }

    public ProductDto registerProduct(ProductRequest request) {

        Product newProduct = new Product.ProductBuilder()
                .setUniqueId(utils.generateProductUniqueId(5))
                .setName(request.getName())
                .setCategory(request.getCategory())
                .setPrice(request.getPrice())
                .setIngredients(request.getIngredients())
                .setCreatedAt(new Date().toInstant())
                .build();

        productRepository.save(newProduct);

        return productMapper.mapToProductDto(newProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage() + id));

        productRepository.delete(product);
    }


    public ProductDto updateProduct(ProductRequest productRequest) {
        Product product = productRepository.findProductByUniqueId(productRequest.getUniqueId())
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage() + productRequest.getUniqueId()
                ));

        Stream.of(product).forEach(v -> {
            if (productRequest.getName() != null) {
                v.setName(productRequest.getName());
            }
            if (productRequest.getPrice() != 0) {
                v.setPrice(productRequest.getPrice());
            }
            if (productRequest.getCategory() != null) {
                v.setCategory(productRequest.getCategory());
            }
            if (productRequest.getIngredients() != null) {
                v.setIngredients(productRequest.getIngredients());
            }
        });

        productRepository.save(product);

        return productMapper.mapToProductDto(product);
    }

    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

}