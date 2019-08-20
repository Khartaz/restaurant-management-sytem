package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.PersonnelDTO;
import com.restaurant.management.mapper.PersonnelMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.LayoutShortcutService;
import com.restaurant.management.service.PersonnelService;
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

    public PersonnelDTO registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelDTO request) {
        AccountUser accountUser = personnelService.registerPerson(currentUser, request);

        shortcutService.assignDefaultShortcut(accountUser.getId());

        return personnelMapper.mapToPersonnelDTO(accountUser);
    }

    public PersonnelDTO updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelDTO request) {
        AccountUser accountUser = personnelService.updatePerson(currentUser, request);

        return personnelMapper.mapToPersonnelDTO(accountUser);
    }

    public Page<PersonnelDTO> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<AccountUser> personnel = personnelService.getAllPersonnel(currentUser, pageable);

        return personnelMapper.mapToPersonnelDTO(personnel);
    }

    public ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        return personnelService.deletePersonById(currentUser, personId);
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds) {
        return personnelService.deleteAllByIds(currentUser, personnelIds);
    }

    public PersonnelDTO getPersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        AccountUser person = personnelService.getPersonById(currentUser, personId);

        return personnelMapper.mapToPersonnelDTO(person);
    }
}
