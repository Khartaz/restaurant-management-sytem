package com.restaurant.management.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api/admin")
//public class AdminUserController {
//
//    private AccountUserFacade accountUserFacade;
//    private AccountUserMapper accountUserMapper;
//
//    @Autowired
//    public AdminUserController(AccountUserFacade accountUserFacade, AccountUserMapper accountUserMapper) {
//        this.accountUserFacade = accountUserFacade;
//        this.accountUserMapper = accountUserMapper;
//    }
//
//    @PostMapping(value = "/signin", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        return ResponseEntity.ok(accountUserFacade.authenticateUser(loginRequest));
//    }
//
//    @PostMapping(value = "/signup", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    Resource<AccountUserResponse> registerAdminUser(@Valid @RequestBody SignUpUserRequest signUpUserRequest) {
//        AccountUserDto accountUserDto = accountUserFacade.registerAdminAccount(signUpUserRequest);
//
//        AccountUserResponse userResponse = accountUserMapper.mapToAccountUserResponse(accountUserDto);
//
//        Link link = linkTo(AdminUserController.class).slash(userResponse.getUserUniqueId()).withSelfRel();
//        return new Resource<>(userResponse, link);
//    }
//
//}