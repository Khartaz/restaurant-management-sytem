package com.restaurant.management.service;

import com.restaurant.management.domain.Ingredient;
import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.exception.product.ProductExsitsException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.mapper.IngredientMapper;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.repository.ProductRepository;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Transactional
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private Utils utils;
    private IngredientMapper ingredientMapper;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          Utils utils,
                          IngredientMapper ingredientMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.utils = utils;
        this.ingredientMapper = ingredientMapper;
    }

    public ProductDto registerProduct(RegisterProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new ProductExsitsException(ProductMessages.PRODUCT_NAME_EXISTS.getMessage());
        }

        String  uniqueProductId = utils.generateProductUniqueId(7);

        Optional<Product> productExists = productRepository.findProductByUniqueId(uniqueProductId);

        if (productExists.isPresent()) {
            uniqueProductId = utils.generateProductUniqueId(8);
        }

        List<Ingredient> ingredients = ingredientMapper.mapToIngredientListFromRequest(request.getIngredients());

        Product newProduct = new Product.ProductBuilder()
                .setUniqueId(uniqueProductId)
                .setName(request.getName())
                .setCategory(request.getCategory())
                .setPrice(request.getPrice())
                .setCreatedAt(new Date().toInstant())
                .setIsArchived(Boolean.FALSE)
                .setIngredients(ingredients)
                .build();

        productRepository.save(newProduct);

        return productMapper.mapToProductDto(newProduct);
    }

    public boolean transferToArchive(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (product.isPresent()) {
            product.get().setArchived(Boolean.TRUE);
            productRepository.save(product.get());
            return true;
        } else {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + uniqueId);
        }
    }

    public ProductDto updateProduct(ProductRequest productRequest) {
        Product product = productRepository.findProductByUniqueId(productRequest.getUniqueId())
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductMessages.PRODUCT_UNIQUE_ID_NOT_FOUND.getMessage() + productRequest.getUniqueId()
                ));

        if (productRepository.existsByName(productRequest.getName())) {
            throw new ProductExsitsException(ProductMessages.PRODUCT_NAME_EXISTS.getMessage());
        }

        Stream.of(product).forEach(p -> {
            p.setName(productRequest.getName());
            p.setPrice(productRequest.getPrice());
            p.setCategory(productRequest.getCategory());
            p.setIngredients(ingredientMapper.mapToIngredientListFromRequest(productRequest.getIngredients()));
        });

        productRepository.save(product);

        return productMapper.mapToProductDto(product);
    }

    public ProductDto getProductByUniqueId(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (!product.isPresent()) {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage());
        }
        return productMapper.mapToProductDto(product.get());
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAllByIsArchivedIsFalse();

        return productMapper.mapToProductDtoList(products);
    }

    public List<ProductDto> getAllArchivedProducts() {
        List<Product> products = productRepository.findAllByIsArchivedIsTrue();

        return productMapper.mapToProductDtoList(products);
    }

}