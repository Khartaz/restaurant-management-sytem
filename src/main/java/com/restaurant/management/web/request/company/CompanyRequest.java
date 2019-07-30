package com.restaurant.management.web.request.company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class CompanyRequest {

    @NotBlank(message = "company name cannot be blank")
    @Size(min = 8, max = 40, message = "company name must be between 8 - 60")
    private String companyName;

    public CompanyRequest() {
    }

    public CompanyRequest(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

}
