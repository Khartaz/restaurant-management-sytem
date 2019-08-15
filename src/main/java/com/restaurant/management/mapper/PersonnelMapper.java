package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import static com.restaurant.management.mapper.RoleMapper.roleToString;

@Component
public final class PersonnelMapper {

    public PersonnelFormDTO mapToPersonnelFormDTO(final AccountUser accountUser) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new PersonnelFormDTO(
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
                roleToString(accountUser),
                accountUser.getAccountUserAddress().getStreetAndNumber(),
                accountUser.getAccountUserAddress().getPostCode(),
                accountUser.getAccountUserAddress().getCity(),
                accountUser.getAccountUserAddress().getCountry(),
                accountUser.isActive()
        );
    }

    public Page<PersonnelFormDTO> mapToPersonnelFormDTO(final Page<AccountUser> personnel) {
        return personnel.map(this::mapToPersonnelFormDTO);
    }
}
