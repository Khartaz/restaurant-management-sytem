package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.Product;
import com.restaurant.management.domain.ecommerce.SessionLineItem;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.exception.product.ProductNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.SessionLineItemRepository;
import com.restaurant.management.repository.ProductRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ProductService;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private SessionLineItemRepository sessionLineItemRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              SessionLineItemRepository sessionLineItemRepository,
                              AccountUserRepository accountUserRepository) {
        this.productRepository = productRepository;
        this.sessionLineItemRepository = sessionLineItemRepository;
        this.accountUserRepository = accountUserRepository;
    }

    private AccountUser getUserById(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    public Product registerProduct(@CurrentUser UserPrincipal currentUser, RegisterProductRequest request) {

        AccountUser accountUser = getUserById(currentUser);

        double price = request.getPrice();
        price = Math.floor(price * 100) / 100;

        Product newProduct = new Product.ProductBuilder()
                .setName(request.getName())
                .setCategory(request.getCategory())
                .setPrice(price)
                .setCompany(accountUser.getCompany())
                .build();

        productRepository.save(newProduct);

        return newProduct;
    }

    public Product updateProduct(ProductRequest productRequest, @CurrentUser UserPrincipal currentUser) {
        Product product = getRestaurantProductById(productRequest.getId(), currentUser);

        Stream.of(product).forEach(p -> {
            p.setName(productRequest.getName());
            p.setPrice(productRequest.getPrice());
            p.setCategory(productRequest.getCategory());
        });

        productRepository.save(product);

        return product;
    }

    public Product getRestaurantProductById(Long productId, @CurrentUser UserPrincipal currentUser) {

        AccountUser accountUser = getUserById(currentUser);

        Long restaurantId = accountUser.getCompany().getId();

        return productRepository.findByIdAndCompanyId(productId, restaurantId)
                .orElseThrow(() -> new ProductNotFoundException(ProductMessages.PRODUCT_ID_NOT_FOUND.getMessage() + productId));
    }

    public Page<Product> getAllByRestaurant(Pageable pageable, @CurrentUser UserPrincipal currentUser) {

        AccountUser accountUser = getUserById(currentUser);

        return productRepository.findByCompany(pageable, accountUser.getCompany());
    }

    public ApiResponse deleteById(Long productId, @CurrentUser UserPrincipal currentUser) {

        getRestaurantProductById(productId, currentUser);

        List<SessionLineItem> sessionLineItems = sessionLineItemRepository.findAllByProductId(productId);

        sessionLineItemRepository.deleteAll(sessionLineItems);

        productRepository.deleteProductById(productId);

        return new ApiResponse(true, ProductMessages.PRODUCT_DELETED.getMessage());
    }
}