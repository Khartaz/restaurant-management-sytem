package com.restaurant.management.web.request.company;

import com.restaurant.management.web.request.user.SignUpUserRequest;

public final class RegisterCompanyRequest {

//    @NotNull(message = "user info cannot be null")
    private SignUpUserRequest signUpUserRequest;

//    @NotNull(message = "company info cannot be null")
    private CompanyRequest companyRequest;

    public RegisterCompanyRequest() {
    }

    public SignUpUserRequest getSignUpUserRequest() {
        return signUpUserRequest;
    }

    public CompanyRequest getCompanyRequest() {
        return companyRequest;
    }
}
