package com.restaurant.management.web.controller;

import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.ProductService;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public @ResponseBody
    Resource<ProductResponse> registerProduct(@RequestBody ProductRequest request) {
        ProductDto productDto = productService.registerProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(response.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok().body(new ApiResponse(true, "Product deleted"));
    }

    @PutMapping
    public @ResponseBody
    Resource<ProductResponse> updateProduct(@RequestBody ProductRequest request) {

        ProductDto productDto = productService.updateProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(request.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @GetMapping(value = " ", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<Product> showProducts() {

        List<Product> productList = productService.getAllProducts();

        Link link = linkTo(ProductController.class).withSelfRel();

        return new Resources<>(productList, link);
    }
}
