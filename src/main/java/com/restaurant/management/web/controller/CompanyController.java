package com.restaurant.management.web.controller;

import com.restaurant.management.domain.ecommerce.dto.CompanyDto;
import com.restaurant.management.mapper.CompanyMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.CompanyAccountUserFacade;
import com.restaurant.management.service.facade.CompanyFacade;
import com.restaurant.management.web.request.company.RegisterCompanyRequest;
import com.restaurant.management.web.response.company.CompanyResponse;
import com.restaurant.management.web.response.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private CompanyAccountUserFacade companyAccountUserFacade;
    private CompanyFacade companyFacade;
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyController(CompanyAccountUserFacade companyAccountUserFacade,
                             CompanyFacade companyFacade,
                             CompanyMapper companyMapper) {
        this.companyAccountUserFacade = companyAccountUserFacade;
        this.companyFacade = companyFacade;
        this.companyMapper = companyMapper;
    }

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<UserResponse> registerCompany(@Valid @RequestBody RegisterCompanyRequest request) {
        UserResponse registerLoginResponse = companyAccountUserFacade.registerCompany(request);

        Link link = linkTo(CompanyController.class).withSelfRel();

        return new Resource<>(registerLoginResponse, link);
    }

    @GetMapping(value = "/my", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<CompanyResponse> getCompany(@CurrentUser UserPrincipal currentUser) {
        CompanyDto companyDto = companyFacade.getCompany(currentUser);

        CompanyResponse response = companyMapper.mapToCompanyResponse(companyDto);

        Link link = linkTo(CompanyController.class).slash("/my").withSelfRel();

        return new Resource<>(response, link);
    }

}
