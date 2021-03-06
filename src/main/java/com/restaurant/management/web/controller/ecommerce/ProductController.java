package com.restaurant.management.web.controller.ecommerce;

import com.restaurant.management.domain.ecommerce.dto.ProductDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductFormDTO;
import com.restaurant.management.domain.ecommerce.dto.ProductHistoryDto;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.facade.ProductFacade;
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
import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductFormDTO> registerProduct(@CurrentUser UserPrincipal currentUser,
                                             @Valid @RequestBody ProductFormDTO request) {

        ProductFormDTO productDto = productFacade.registerProduct(currentUser, request);

        Link link = linkTo(ProductController.class).withSelfRel();

        return new Resource<>(productDto, link);
    }

    @RolesAllowed({"ROLE_MANAGER"})
    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductFormDTO> updateProduct(@Valid @RequestBody ProductFormDTO request,
                                       @CurrentUser UserPrincipal currentUser) {

        ProductFormDTO productDto = productFacade.updateProduct(currentUser, request);

        Link link = linkTo(ProductController.class).slash(productDto.getId()).withSelfRel();
        return new Resource<>(productDto, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<ProductFormDTO>> getAllByRestaurant(Pageable pageable,
                                                                      @CurrentUser UserPrincipal currentUser,
                                                                      PagedResourcesAssembler assembler) {
        Page<ProductFormDTO> productDTOPage = productFacade.getAllByRestaurant(pageable, currentUser);

        if (!productDTOPage.hasContent()) {
            PagedResources pagedResources = assembler.toEmptyResource(productDTOPage, ProductFormDTO.class);
            return new ResponseEntity<PagedResources<ProductFormDTO>>(pagedResources, HttpStatus.OK);
        }

        return new ResponseEntity<>(assembler.toResource(productDTOPage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductDTO> showRestaurantProductById(@PathVariable Long id,
                                                        @CurrentUser UserPrincipal currentUser) {

        ProductDTO productFormDTO = productFacade.getRestaurantProductById(id, currentUser);

        Link link = linkTo(ProductController.class).slash(productFormDTO.getId()).withSelfRel();

        return new Resource<>(productFormDTO, link);
    }

    @RolesAllowed({"ROLE_MANAGER"})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id,
                                        @CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok().body(productFacade.deleteById(id, currentUser));
    }

    @RolesAllowed({"ROLE_MANAGER"})
    @DeleteMapping(value = "/delete/{productsIds}")
    public ResponseEntity<?> deleteAllById(@PathVariable Long[] productsIds,
                                           @CurrentUser UserPrincipal currentUser) {

        return ResponseEntity.ok().body(productFacade.deleteAllByIds(productsIds, currentUser));
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
