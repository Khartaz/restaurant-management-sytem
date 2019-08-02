package com.restaurant.management.web.controller;

import com.restaurant.management.domain.ecommerce.dto.ProductDto;
import com.restaurant.management.domain.ecommerce.dto.ProductHistoryDto;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.ProductFacade;
import com.restaurant.management.web.request.product.ProductRequest;
import com.restaurant.management.web.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
@Transactional
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
    Resource<ProductDto> registerProduct(@CurrentUser UserPrincipal currentUser,
                                              @Valid @RequestBody ProductRequest request) {

        ProductDto productDto = productFacade.registerProduct(currentUser, request);

        Link link = linkTo(ProductController.class).withSelfRel();

        return new Resource<>(productDto, link);
    }

//    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    Resource<ProductResponse> registerProduct(@CurrentUser UserPrincipal currentUser,
//                                               @Valid @RequestBody RegisterProductRequest request) {
//        ProductDto productDto = productFacade.registerProduct(currentUser, request);
//
//        ProductResponse response = productMapper.mapToProductResponse(productDto);
//
//        Link link = linkTo(ProductController.class).slash(response.getId()).withSelfRel();
//
//        return new Resource<>(response, link);
//    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductRequest> updateProduct(@Valid @RequestBody ProductRequest request,
                                            @CurrentUser UserPrincipal currentUser) {

        ProductRequest productRequest = productFacade.updateProduct(request, currentUser);

        Link link = linkTo(ProductController.class).slash(request.getId()).withSelfRel();
        return new Resource<>(productRequest, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<ProductRequest>> getAllByRestaurant(Pageable pageable,
                                                                       @CurrentUser UserPrincipal currentUser,
                                                                       PagedResourcesAssembler assembler) {
        Page<ProductRequest> dtoPage = productFacade.getAllByRestaurant(pageable, currentUser);

//        Page<ProductResponse> responsePage = productMapper.mapToProductResponsePage(dtoPage);

        return new ResponseEntity<>(assembler.toResource(dtoPage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> showRestaurantProductById(@PathVariable Long id,
                                                        @CurrentUser UserPrincipal currentUser) {

        ProductDto productDto = productFacade.getRestaurantProductById(id, currentUser);

        ProductResponse response = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(response.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id,
                                        @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(productFacade.deleteById(id, currentUser));
    }

    @GetMapping(value = "/history/{productId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<ProductHistoryDto> showHistory(@PathVariable Long productId,
                                             @CurrentUser UserPrincipal currentUser) {
        List<ProductHistoryDto> productHistory = productFacade.getProductHistory(productId, currentUser);

        Link link = linkTo(ProductController.class).slash("history").slash(productId).withSelfRel();

        return new Resources<>(productHistory, link);
    }

}
