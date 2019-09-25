package com.restaurant.management.mapper.ecommerce;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.dto.PersonnelDTO;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

import static com.restaurant.management.mapper.ecommerce.RoleMapper.roleToString;

@Component
public final class PersonnelMapper {

    private AddressMapper addressMapper;

    @Autowired
    public PersonnelMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public PersonnelDTO mapToPersonnelDTO(final User user) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return new PersonnelDTO(
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
                roleToString(user),
                user.isActive(),
                addressMapper.mapToAddressDTO(user.getUserAddress())
        );
    }

    public PersonnelFormDTO mapToPersonnelFormDTO(final User user) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return new PersonnelFormDTO(
                formatter.format(user.getCreatedAt()),
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getJobTitle(),
                roleToString(user),
                user.isActive(),
                user.getUserAddress().getStreetAndNumber(),
                user.getUserAddress().getPostCode(),
                user.getUserAddress().getCity(),
                user.getUserAddress().getCountry()
        );
    }

    public Page<PersonnelDTO> mapToPersonnelDTO(final Page<User> personnel) {
        return personnel.map(this::mapToPersonnelDTO);
    }

    public Page<PersonnelFormDTO> mapToPersonnelFormDTOPage(final Page<User> personnel) {
        return personnel.map(this::mapToPersonnelFormDTO);
    }
}
