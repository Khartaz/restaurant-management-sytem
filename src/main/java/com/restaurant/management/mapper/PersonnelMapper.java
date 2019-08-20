package com.restaurant.management.mapper;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.AccountUserAddress;
import com.restaurant.management.domain.ecommerce.dto.PersonnelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import static com.restaurant.management.mapper.RoleMapper.roleToString;

@Component
public final class PersonnelMapper {

    private AddressMapper addressMapper;

    @Autowired
    public PersonnelMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public PersonnelDTO mapToPersonnelDTO(final AccountUser accountUser) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new PersonnelDTO(
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
                accountUser.isActive(),
                addressMapper.mapToAddressDTO(accountUser.getAccountUserAddress())
        );
    }

    public Page<PersonnelDTO> mapToPersonnelDTO(final Page<AccountUser> personnel) {
        return personnel.map(this::mapToPersonnelDTO);
    }
}
