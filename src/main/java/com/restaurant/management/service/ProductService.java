package com.restaurant.management.service;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.exception.product.ProductExsitsException;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
        if (productRepository.existsByName(request.getName())) {
            throw new ProductExsitsException(ProductMessages.PRODUCT_NAME_EXISTS.getErrorMessage());
        }

        String  uniqueProductId = utils.generateProductUniqueId(7);

        Optional<Product> productExists = productRepository.findProductByUniqueId(uniqueProductId);

        if (productExists.isPresent()) {
            uniqueProductId = utils.generateProductUniqueId(8);
        }

        Product newProduct = new Product.ProductBuilder()
                .setUniqueId(uniqueProductId)
                .setName(request.getName())
                .setCategory(request.getCategory())
                .setPrice(request.getPrice())
                .setIngredients(request.getIngredients())
                .setCreatedAt(new Date().toInstant())
                .build();

        productRepository.save(newProduct);

        return productMapper.mapToProductDto(newProduct);
    }

    public boolean deleteProduct(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (product.isPresent()) {
            productRepository.deleteById(product.get().getId());
            return true;
        } else {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage() + uniqueId);
        }
    }

    public ProductDto updateProduct(ProductRequest productRequest) {
        Product product = productRepository.findProductByUniqueId(productRequest.getUniqueId())
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage() + productRequest.getUniqueId()
                ));

        Stream.of(product).forEach(p -> {
            p.setName(productRequest.getName());
            p.setPrice(productRequest.getPrice());
            p.setCategory(productRequest.getCategory());
            p.setIngredients(productRequest.getIngredients());
        });

        productRepository.save(product);

        return productMapper.mapToProductDto(product);
    }

    public ProductDto getProductByUniqueId(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (!product.isPresent()) {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_NOT_FOUND.getErrorMessage());
        }

        return productMapper.mapToProductDto(product.get());
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(v -> productMapper.mapToProductDto(v))
                .collect(Collectors.toList());
    }

}