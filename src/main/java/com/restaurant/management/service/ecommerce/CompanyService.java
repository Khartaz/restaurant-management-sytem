package com.restaurant.management.service.ecommerce;

import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.dto.CompanyDTO;
import com.restaurant.management.domain.ecommerce.dto.CompanyFormDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.company.RegisterCompanyRequest;
import com.restaurant.management.web.response.company.RegisterCompany;

public interface CompanyService {

    RegisterCompany registerCompany(RegisterCompanyRequest registerCompanyRequest);

    Company getCompanyById(@CurrentUser UserPrincipal currentUser);

    Company updateCompanyInfo(@CurrentUser UserPrincipal currentUser, CompanyFormDTO companyFormDTO);
}
