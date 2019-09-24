package com.restaurant.management.service.ecommerce.facade;


import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.dto.CompanyFormDTO;
import com.restaurant.management.mapper.ecommerce.CompanyMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.CompanyService;
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

    public CompanyFormDTO getCompany(@CurrentUser UserPrincipal currentUser) {
        Company company = companyService.getCompanyById(currentUser);

        return companyMapper.mapToCompanyFormDTO(company);
    }

    public CompanyFormDTO updateCompany(@CurrentUser UserPrincipal currentUser, CompanyFormDTO companyFormDTO) {
        Company company = companyService.updateCompanyInfo(currentUser, companyFormDTO);

        return companyMapper.mapToCompanyFormDTO(company);
    }
}
