package com.restaurant.management.web.controller;

import com.restaurant.management.domain.ecommerce.dto.CompanyFormDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.facade.CompanyAccountUserFacade;
import com.restaurant.management.service.ecommerce.facade.CompanyFacade;
import com.restaurant.management.web.request.company.RegisterCompanyRequest;
import com.restaurant.management.web.response.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private CompanyAccountUserFacade companyAccountUserFacade;
    private CompanyFacade companyFacade;

    @Autowired
    public CompanyController(CompanyAccountUserFacade companyAccountUserFacade,
                             CompanyFacade companyFacade) {
        this.companyAccountUserFacade = companyAccountUserFacade;
        this.companyFacade = companyFacade;
    }

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<UserResponse> registerCompany(@Valid @RequestBody RegisterCompanyRequest request) {
        UserResponse registerLoginResponse = companyAccountUserFacade.registerCompany(request);

        Link link = linkTo(CompanyController.class).withSelfRel();

        return new Resource<>(registerLoginResponse, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CompanyFormDTO> getCompany(@CurrentUser UserPrincipal currentUser) {
        CompanyFormDTO companyDto = companyFacade.getCompany(currentUser);

        Link link = linkTo(CompanyController.class).slash(companyDto.getId()).withSelfRel();

        return new Resource<>(companyDto, link);
    }

    @RolesAllowed({"ROLE_MANAGER"})
    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CompanyFormDTO> updateCompanyInfo(@CurrentUser UserPrincipal userPrincipal,
                                               @RequestBody CompanyFormDTO request) {
        CompanyFormDTO companyFormDTO = companyFacade.updateCompany(userPrincipal, request);

        Link link = linkTo(CompanyController.class).slash(companyFormDTO.getId()).withSelfRel();

        return new Resource<>(companyFormDTO, link);
    }

}
