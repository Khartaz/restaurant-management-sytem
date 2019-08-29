package com.restaurant.management.web.request.company;

import com.restaurant.management.web.request.user.SignUpUserRequest;

public final class RegisterCompanyRequest {

    private SignUpUserRequest signUpUserRequest;

    private CompanyRequest companyRequest;

    public RegisterCompanyRequest() {
    }

    public RegisterCompanyRequest(SignUpUserRequest signUpUserRequest,
                                  CompanyRequest companyRequest) {
        this.signUpUserRequest = signUpUserRequest;
        this.companyRequest = companyRequest;
    }

    public SignUpUserRequest getSignUpUserRequest() {
        return signUpUserRequest;
    }

    public CompanyRequest getCompanyRequest() {
        return companyRequest;
    }
}
