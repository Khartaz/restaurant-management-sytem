package com.restaurant.management.service;

import com.restaurant.management.domain.Ingredient;
import com.restaurant.management.domain.Product;
import com.restaurant.management.mapper.IngredientMapper;
import com.restaurant.management.repository.ProductRepository;
import com.restaurant.management.web.request.product.IngredientRequest;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTestSuite {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private IngredientMapper ingredientMapper;

    private static final String UNIQUE_ID = "JD3ND2E";
    private static final String PRODUCT_NAME = "Product name";
    private static final String PRODUCT_CATEGORY = "Product category";
    private static final Double PRODUCT_PRICE = 2.20;
    private static final String INGREDIENT = "Ingredient";

    @Test
    public void shouldRegisterProduct() {
        //GIVEN
        List<IngredientRequest> ingredientsRequest = new ArrayList<>();
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));

        RegisterProductRequest request = new RegisterProductRequest(
                PRODUCT_NAME, PRODUCT_CATEGORY, PRODUCT_PRICE, ingredientsRequest);

        when(ingredientMapper.mapToIngredientListFromRequest(ingredientsRequest)).thenCallRealMethod();
        //WHEN
        Product result = productService.registerProduct(request);
        //THEN
        assertAll(
                () -> assertEquals(result.getUniqueId().length(), 7),
                () -> assertEquals(result.getIngredients().get(0).getName(), request.getIngredients().get(0).getName()),
                () -> assertEquals(result.getCategory(), request.getCategory()),
                () -> assertEquals(result.getPrice().doubleValue(), request.getPrice())
        );
    }

    @Test
    public void shouldUpdateProduct() {
        //GIVEN
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(INGREDIENT));
        ingredients.add(new Ingredient(INGREDIENT));
        ingredients.add(new Ingredient(INGREDIENT));

        Product product = new Product(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                ingredients
        );

        List<IngredientRequest> ingredientsRequest = new ArrayList<>();
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));
        ingredientsRequest.add(new IngredientRequest(INGREDIENT));

        ProductRequest request = new ProductRequest(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                3.50,
                ingredientsRequest
        );

        when(productRepository.findProductByUniqueId(UNIQUE_ID)).thenReturn(Optional.of(product));
        when(productRepository.existsByName(request.getName())).thenReturn(Boolean.FALSE);
        //WHEN
        Product result = productService.updateProduct(request);
        //THEN
        assertAll(
                () -> assertEquals(result.getCategory(), request.getCategory()),
                () -> assertTrue(!result.getPrice().equals(PRODUCT_PRICE)),
                () -> assertEquals(result.getUniqueId(), request.getUniqueId())
        );
    }

    @Test
    public void shouldGetProductByUniqueId() {
        //GIVEN
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(INGREDIENT));
        ingredients.add(new Ingredient(INGREDIENT));
        ingredients.add(new Ingredient(INGREDIENT));

        Optional<Product> optionalProduct = Optional.of(new Product(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                ingredients
        ));

        when(productRepository.findProductByUniqueId(UNIQUE_ID)).thenReturn(optionalProduct);
        //WHEN
        Product result = productService.getProductByUniqueId(UNIQUE_ID);
        //THEN
        Product product = optionalProduct.get();
        assertAll(
                () -> assertEquals(result.getCategory(), product.getCategory()),
                () -> assertEquals(result.getPrice(), product.getPrice()),
                () -> assertEquals(result.getIngredients().size(), product.getIngredients().size())
        );
    }

    @Test
    public void shouldGetAllProducts() {
        //GIVEN
        Product product1 = new Product(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                Collections.singletonList(new Ingredient())
        );

        Product product2 = new Product(
                UNIQUE_ID + 1,
                PRODUCT_NAME + 1,
                PRODUCT_CATEGORY + 1,
                PRODUCT_PRICE + 1,
                Collections.singletonList(new Ingredient())
        );

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Pageable pageable = PageRequest.of(0,1);

        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(products));
        //WHEN
        Page<Product> productsPage = productService.getAllProducts(pageable);
        List<Product> result = productsPage.get().collect(Collectors.toList());
        //THEN
        assertAll(
                () -> assertEquals(result.size(), 2),
                () -> assertEquals(result.get(0).getPrice(), product1.getPrice()),
                () -> assertEquals(result.get(1).getCategory(), product2.getCategory())
        );
    }

}