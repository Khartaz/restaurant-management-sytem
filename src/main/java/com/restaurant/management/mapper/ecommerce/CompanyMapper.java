package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.dto.CompanyDTO;
import com.restaurant.management.domain.ecommerce.dto.CompanyFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CompanyMapper {

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public CompanyDTO mapToCompanyDto(final Company company) {
        return new CompanyDTO(
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getCreatedByUserId(),
                company.getUpdatedByUserId(),
                company.getId(),
                company.getName(),
                company.getPhone(),
                addressMapper.mapToAddressDto(company.getCompanyAddress())
        );
    }

    public CompanyFormDTO mapToCompanyFormDTO(final Company company) {
        return new CompanyFormDTO(
                company.getCreatedAt(),
                company.getId(),
                company.getName(),
                company.getPhone(),
                company.getCompanyAddress().getStreetAndNumber(),
                company.getCompanyAddress().getPostCode(),
                company.getCompanyAddress().getCity(),
                company.getCompanyAddress().getCountry()
        );
    }
}
