package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogExecutionTime;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.service.facade.ProductFacade;
import com.restaurant.management.web.request.account.LoginRequest;
import com.restaurant.management.web.request.account.SignUpUserRequest;
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

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@RolesAllowed({"ROLE_ADMIN"})
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

    @GetMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<ProductResponse>> showProductsPageable(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<ProductDto> dtoPage = productFacade.getAllProducts(pageable);

        Page<ProductResponse> responsePage = productMapper.mapToProductResponsePage(dtoPage);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}",produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<ProductResponse> showProduct(@PathVariable Long id) {

        ProductDto productDto = productFacade.getProductById(id);

        ProductResponse productResponse = productMapper.mapToProductResponse(productDto);

        Link link = linkTo(ProductController.class).slash(productResponse.getId()).withSelfRel();

        return new Resource<>(productResponse, link);
    }

    @LogExecutionTime
    @GetMapping(value = "/all-accounts", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<AccountUserResponse>> showAllUsersPageable(Pageable pageable,
                                                                             PagedResourcesAssembler assembler) {
        Page<AccountUserDto> accountUsersDto = accountUserFacade.getAllAccountUsers(pageable);

        Page<AccountUserResponse> responsePage = accountUserMapper.mapToAccountUserResponsePage(accountUsersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long id) {
        return ResponseEntity.ok().body(accountUserFacade.deleteUserById(id));
    }

    @GetMapping(value = "/users/{id}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> showUser(@PathVariable Long id) {
        AccountUserDto accountUserDto = accountUserFacade.getUserById(id);

        AccountUserResponse accountUserResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(accountUserResponse.getId()).withSelfRel();

        return new Resource<>(accountUserResponse, link);
    }
}