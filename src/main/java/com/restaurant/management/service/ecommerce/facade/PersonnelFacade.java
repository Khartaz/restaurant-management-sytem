package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.dto.PersonnelDTO;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.mapper.ecommerce.PersonnelMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.LayoutShortcutService;
import com.restaurant.management.service.ecommerce.PersonnelService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PersonnelFacade {

    private PersonnelMapper personnelMapper;
    private PersonnelService personnelService;
    private LayoutShortcutService shortcutService;

    @Autowired
    public PersonnelFacade(PersonnelMapper personnelMapper,
                           PersonnelService personnelService,
                           LayoutShortcutService shortcutService) {
        this.personnelMapper = personnelMapper;
        this.personnelService = personnelService;
        this.shortcutService = shortcutService;
    }

    public PersonnelFormDTO registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        User user = personnelService.registerPerson(currentUser, request);

        shortcutService.assignDefaultShortcut(user.getId());

        return personnelMapper.mapToPersonnelFormDTO(user);
    }

    public PersonnelFormDTO updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        User user = personnelService.updatePerson(currentUser, request);

        return personnelMapper.mapToPersonnelFormDTO(user);
    }

    public Page<PersonnelFormDTO> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<User> personnel = personnelService.getAllPersonnel(currentUser, pageable);

        return personnelMapper.mapToPersonnelFormDTOPage(personnel);
    }

    public ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        return personnelService.deletePersonById(currentUser, personId);
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds) {
        return personnelService.deleteAllByIds(currentUser, personnelIds);
    }

    public PersonnelDTO getPersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        User person = personnelService.getPersonById(currentUser, personId);

        return personnelMapper.mapToPersonnelDTO(person);
    }
}
