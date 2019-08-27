package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.AccountUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.management.mapper.RoleMapper.roleToString;

@Component
@SuppressWarnings("Duplicates")
public final class AccountUserMapper {

    private CompanyMapper companyMapper;

    @Autowired
    public AccountUserMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public AccountUserDTO mapToAccountUserDto(final AccountUser accountUser) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new AccountUserDTO(
                formatter.format(accountUser.getCreatedAt()),
                formatter.format(accountUser.getUpdatedAt()),
                accountUser.getCreatedByUserId(),
                accountUser.getUpdatedByUserId(),
                accountUser.getId(),
                accountUser.getName(),
                accountUser.getLastName(),
                accountUser.getEmail(),
                accountUser.getPhone(),
                accountUser.getJobTitle(),
                accountUser.isActive(),
                roleToString(accountUser),
                accountUser.getAccountUserAddress().getStreetAndNumber(),
                accountUser.getAccountUserAddress().getPostCode(),
                accountUser.getAccountUserAddress().getCity(),
                accountUser.getAccountUserAddress().getCountry(),
                companyMapper.mapToCompanyDto(accountUser.getCompany())
        );
    }

    public List<AccountUserDTO> mapToAccountUserListDto(final List<AccountUser> accountUsers) {
        return accountUsers.stream()
                .map(this::mapToAccountUserDto)
                .collect(Collectors.toList());
    }

    public Page<AccountUserDTO> mapToAccountUserDtoPage(final Page<AccountUser> accountUsers) {
        return accountUsers.map(this::mapToAccountUserDto);
    }
}
