package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.Company;
import com.restaurant.management.domain.ecommerce.dto.CompanyDto;
import com.restaurant.management.web.response.company.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CompanyMapper {

    private AddressMapper addressMapper;

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public CompanyResponse mapToCompanyResponse(final Company company) {
        return new CompanyResponse(
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getCreatedByUserId(),
                company.getUpdatedByUserId(),
                company.getId(),
                company.getName(),
                addressMapper.mapToAddressResponse(company.getCompanyAddress())
        );
    }

    public CompanyDto mapToCompanyDto(final Company company) {
        return new CompanyDto(
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getCreatedByUserId(),
                company.getUpdatedByUserId(),
                company.getId(),
                company.getName(),
                addressMapper.mapToAddressDto(company.getCompanyAddress())
        );
    }

    public CompanyResponse mapToCompanyResponse(final CompanyDto companyDto) {
        return new CompanyResponse(
                companyDto.getCreatedAt(),
                companyDto.getUpdatedAt(),
                companyDto.getCreatedByUserId(),
                companyDto.getUpdatedByUserId(),
                companyDto.getId(),
                companyDto.getName(),
                addressMapper.mapToAddressResponse(companyDto.getAddressDto())
        );
    }
}
