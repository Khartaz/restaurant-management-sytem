package com.restaurant.management.web.controller;

import com.restaurant.management.config.LogExecutionTime;
import com.restaurant.management.config.LogLogin;
import com.restaurant.management.domain.dto.AccountUserDto;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.AccountUserFacade;
import com.restaurant.management.web.request.LoginRequest;
import com.restaurant.management.web.request.SignUpUserRequest;
import com.restaurant.management.web.request.UpdateAccountNameOrLastname;
import com.restaurant.management.web.response.user.AccountUserResponse;
import com.restaurant.management.web.response.user.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountUserController {

    private AccountUserFacade accountUserFacade;
    private AccountUserMapper accountUserMapper;

    @Autowired
    public AccountUserController(AccountUserFacade accountUserFacade,
                                 AccountUserMapper accountUserMapper) {
        this.accountUserFacade = accountUserFacade;
        this.accountUserMapper = accountUserMapper;
    }

    @GetMapping(value = "/me")
//    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @LogLogin
    @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(accountUserFacade.authenticateUser(loginRequest));
    }

    @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> registerUserAccount(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
        AccountUserDto accountUserDto = accountUserFacade.registerManagerAccount(signUpUserRequest);

        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(userResponse.getUserUniqueId()).withSelfRel();
        return new Resource<>(userResponse, link);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> updateAccountNameOrLastname(@Valid @RequestBody UpdateAccountNameOrLastname request) {

        AccountUserDto accountUserDto = accountUserFacade.updateAccountNameOrLastname(request);

        AccountUserResponse response = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(response.getUserUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    //Move this controller to AdminController?
//    @RolesAllowed({"ROLE_ADMIN"})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable Long id) {
        return ResponseEntity.ok().body(accountUserFacade.deleteUserById(id));
    }

    @LogExecutionTime
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<AccountUserResponse>> showAllUsersPageable(Pageable pageable,
                                                                             PagedResourcesAssembler assembler) {
        Page<AccountUserDto> accountUsersDto = accountUserFacade.getAllAccountUsers(pageable);

        Page<AccountUserResponse> responsePage = accountUserMapper.mapToAccountUserResponsePage(accountUsersDto);

        return new ResponseEntity<>(assembler.toResource(responsePage), HttpStatus.OK);
    }

//    @RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
//    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{userUniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<AccountUserResponse> showUser(@PathVariable String userUniqueId) {
        AccountUserDto accountUserDto = accountUserFacade.getUserByUserUniqueId(userUniqueId);

        AccountUserResponse accountUserResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);

        Link link = linkTo(AccountUserController.class).slash(accountUserResponse.getUserUniqueId()).withSelfRel();

        return new Resource<>(accountUserResponse, link);
    }

}
