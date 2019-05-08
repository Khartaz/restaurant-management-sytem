package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.service.facade.ProductFacade;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.response.ProductResponse;
import com.restaurant.management.web.response.user.AccountUserResponse;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RolesAllowed({"ROLE_ADMIN"})
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private AccountUserFacade accountUserFacade;
    private AccountUserMapper accountUserMapper;
    private ProductFacade productFacade;
    private ProductMapper productMapper;

    @Autowired
    public AdminUserController(AccountUserFacade accountUserFacade,
                               AccountUserMapper accountUserMapper,
                               ProductFacade productFacade,
                               ProductMapper productMapper) {
        this.accountUserFacade = accountUserFacade;
        this.accountUserMapper = accountUserMapper;
        this.productFacade = productFacade;
        this.productMapper = productMapper;
    }

    @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserFacade.authenticateUser(loginRequest));
    }

    @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> registerAdminUser(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        AccountUserDto accountUserDto = accountUserFacade.registerAdminAccount(signUpUserRequest);

        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AdminUserController.class).slash(userResponse.getId()).withSelfRel();
        return new Resource<>(userResponse, link);
    }

    @GetMapping(value = "/all-products", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<ProductResponse>> showProductsPageable(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<ProductDto> dtoPage = productFacade.getAllProducts(pageable);

        Page<ProductResponse> responsePage = productMapper.mapToProductResponsePage(dtoPage);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> showProduct(@PathVariable Long id) {

        ProductDto productDto = productFacade.getProductById(id);

        ProductResponse productResponse = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(productResponse.getId()).withSelfRel();

        return new Resource<>(productResponse, link);
    }

}