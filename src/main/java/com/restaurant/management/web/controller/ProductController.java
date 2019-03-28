package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.facade.ProductFacade;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.request.product.RegisterProductRequest;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductFacade productFacade;
    private ProductMapper productMapper;

    @Autowired
    public ProductController(ProductFacade productFacade,
                             ProductMapper productMapper) {
        this.productFacade = productFacade;
        this.productMapper = productMapper;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> registerProduct(@Valid @RequestBody RegisterProductRequest request) {
        ProductDto productDto = productFacade.registerProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(response.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> updateProduct(@Valid @RequestBody ProductRequest request) {

        ProductDto productDto = productFacade.updateProduct(request);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(request.getUniqueId()).withSelfRel();
        return new Resource<>(response, link);
    }

    @GetMapping(value = "/{uniqueId}",produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> showProduct(@PathVariable String uniqueId) {

        ProductDto productDto = productFacade.getProductByUniqueId(uniqueId);

        ProductResponse productResponse = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(productResponse.getUniqueId()).withSelfRel();

        return new Resource<>(productResponse, link);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<?> deleteByUniqueId(@PathVariable String uniqueId) {
        return ResponseEntity.ok().body(productFacade.deleteByUniqueId(uniqueId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<ProductResponse>> showProductsPageable(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<ProductDto> dtoPage = productFacade.getAllProducts(pageable);

        Page<ProductResponse> responsePage = productMapper.mapToProductResponsePage(dtoPage);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }
}
