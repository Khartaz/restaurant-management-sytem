package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogExecutionTime;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.web.request.user.SignUpUserRequest;
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

//@RolesAllowed({"ROLE_ADMIN"})
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminUserController {

    private AccountUserFacade accountUserFacade;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AdminUserController(AccountUserFacade accountUserFacade,
                               AccountUserMapper accountUserMapper) {
        this.accountUserFacade = accountUserFacade;
        this.accountUserMapper = accountUserMapper;
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

    @RolesAllowed({"ROLE_ADMIN"})
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