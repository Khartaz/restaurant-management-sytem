package com.restaurant.management.service.facade;


import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.dto.CompanyDto;
import com.restaurant.management.mapper.CompanyMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CompanyFacade {
    private CompanyService companyService;
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyFacade(CompanyService companyService,
                         CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    public CompanyDto getCompany(@CurrentUser UserPrincipal currentUser) {
        Company company = companyService.getCompanyById(currentUser);

        return companyMapper.mapToCompanyDto(company);
    }
}
