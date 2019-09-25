package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static com.restaurant.management.mapper.ecommerce.RoleMapper.roleToString;

@Component
@SuppressWarnings("Duplicates")
public final class UserMapper {

    private CompanyMapper companyMapper;

    @Autowired
    public UserMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public UserDTO mapToAccountUserDTO(final User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new UserDTO(
                formatter.format(user.getCreatedAt()),
                formatter.format(user.getUpdatedAt()),
                user.getCreatedByUserId(),
                user.getUpdatedByUserId(),
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getJobTitle(),
                user.isActive(),
                roleToString(user),
                user.getUserAddress().getStreetAndNumber(),
                user.getUserAddress().getPostCode(),
                user.getUserAddress().getCity(),
                user.getUserAddress().getCountry(),
                companyMapper.mapToCompanyDto(user.getCompany())
        );
    }

    public List<UserDTO> mapToAccountUserListDto(final List<User> users) {
        return users.stream()
                .map(this::mapToAccountUserDTO)
                .collect(Collectors.toList());
    }

    public Page<UserDTO> mapToAccountUserDtoPage(final Page<User> accountUsers) {
        return accountUsers.map(this::mapToAccountUserDTO);
    }
}
