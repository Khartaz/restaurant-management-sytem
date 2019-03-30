package com.restaurant.management.service;

import com.restaurant.management.domain.Ingredient;
import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.SessionLineItem;
import com.restaurant.management.exception.product.ProductExsitsException;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.mapper.IngredientMapper;
import com.restaurant.management.repository.SessionLineItemRepository;
import com.restaurant.management.repository.ProductRepository;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private IngredientMapper ingredientMapper;
    private SessionLineItemRepository sessionLineItemRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          IngredientMapper ingredientMapper,
                          SessionLineItemRepository sessionLineItemRepository) {
        this.productRepository = productRepository;
        this.ingredientMapper = ingredientMapper;
        this.sessionLineItemRepository = sessionLineItemRepository;
    }

    public Product registerProduct(RegisterProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new ProductExsitsException(ProductMessages.PRODUCT_NAME_EXISTS.getMessage());
        }

        String uniqueProductId = Utils.generateProductUniqueId(7);

        List<Ingredient> ingredients = ingredientMapper.mapToIngredientListFromRequest(request.getIngredients());

        double price = request.getPrice();
        price = Math.floor(price * 100) / 100;

        Product newProduct = new Product.ProductBuilder()
                .setUniqueId(uniqueProductId)
                .setName(request.getName())
                .setCategory(request.getCategory())
                .setPrice(price)
                .setCreatedAt(new Date().toInstant())
                .setIngredients(ingredients)
                .build();

        productRepository.save(newProduct);

        return newProduct;
    }

    public Product updateProduct(ProductRequest productRequest) {
        Product product = productRepository.findProductByUniqueId(productRequest.getUniqueId())
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductMessages.PRODUCT_UNIQUE_ID_NOT_FOUND.getMessage() + productRequest.getUniqueId()
                ));

        if (productRepository.existsByName(productRequest.getName())) {
            throw new ProductExsitsException(ProductMessages.PRODUCT_NAME_EXISTS.getMessage());
        }

        List<Ingredient> ingredients = ingredientMapper.mapToIngredientListFromRequest(productRequest.getIngredients());

        Stream.of(product).forEach(p -> {
            p.setName(productRequest.getName());
            p.setPrice(productRequest.getPrice());
            p.setCategory(productRequest.getCategory());
            p.setIngredients(ingredients);
        });

        productRepository.save(product);

        return product;
    }

    public Product getProductByUniqueId(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (!product.isPresent()) {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage());
        }
        return product.get();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ApiResponse deleteByUniqueId(String uniqueId) {
        Optional<Product> product = productRepository.findProductByUniqueId(uniqueId);

        if (product.isPresent()) {
            List<SessionLineItem> sessionLineItems = sessionLineItemRepository.findAllByProductUniqueId(uniqueId);

            sessionLineItemRepository.deleteAll(sessionLineItems);

            productRepository.deleteByUniqueId(uniqueId);

            return new ApiResponse(true, ProductMessages.PRODUCT_DELETED.getMessage());
        } else {
            throw new ProductNotFoundException(ProductMessages.PRODUCT_UNIQUE_ID_NOT_FOUND.getMessage() + uniqueId);
        }
    }

    public List<Product> getAllByName(String name, Pageable pageable) {
        return productRepository.findAllByName(name, pageable);
    }
}