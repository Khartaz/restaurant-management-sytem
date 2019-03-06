package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.exception.product.ProductMessages;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.ProductService;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> registerProduct(@Valid @RequestBody RegisterProductRequest request) {
        ProductDto productDto = productService.registerProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(response.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String uniqueId) {
        productService.deleteProduct(uniqueId);
        return ResponseEntity.ok().body(new ApiResponse(true, ProductMessages.PRODUCT_DELETED.getErrorMessage()));
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> updateProduct(@Valid @RequestBody ProductRequest request) {

        ProductDto productDto = productService.updateProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(request.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<ProductResponse> showProducts() {

        List<ProductDto> productsDto = productService.getAllProducts();

        List<ProductResponse> productsResponse = productMapper.mapToProductResponseList(productsDto);

        Link link = linkTo(ProductController.class).withSelfRel();

        return new Resources<>(productsResponse, link);
    }

    @GetMapping(value = "/{uniqueId}",produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> showProduct(@PathVariable String uniqueId) {

        ProductDto productDto = productService.getProductByUniqueId(uniqueId);

        ProductResponse productResponse = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(productResponse.getUniqueId()).withSelfRel();

        return new Resource<>(productResponse, link);
    }
}
