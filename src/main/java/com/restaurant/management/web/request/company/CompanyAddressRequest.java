package com.restaurant.management.web.request.company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public final class CompanyAddressRequest {

    @NotBlank(message = "street and number cannot be blank")
    @Size(min = 8, max = 40, message = "street and number name must be between 8 - 40")
    private String streetAndNumber;

    @NotBlank(message = "post code cannot be blank")
    @Size(min = 4, max = 12, message = "post code value must be between 4 - 12")
    private String postCode;

    @NotBlank(message = "city cannot be blank")
    @Size(min = 4, max = 50, message = "city name must be between 4 - 50")
    private String city;

    @NotBlank(message = "country name cannot be blank")
    @Size(min = 4, max = 80, message = "country must be between 4 - 80")
    private String country;

    public CompanyAddressRequest() {

    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
