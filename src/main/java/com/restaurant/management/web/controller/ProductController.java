package com.restaurant.management.web.controller;

import com.restaurant.management.service.ProductService;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> registerProduct(@RequestBody ProductRequest request) {
        productService.registerProduct(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/product/{name}")
                .buildAndExpand(request.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Product register"));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok().body(new ApiResponse(true, "Product deleted"));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable  Long id, @RequestBody ProductRequest request) {

        productService.updateProduct(id, request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/product/{name}")
                .buildAndExpand(request.getName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Product Updated"));
    }
}
