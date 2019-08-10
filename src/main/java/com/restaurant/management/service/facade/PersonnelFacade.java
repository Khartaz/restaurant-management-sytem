package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
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

    public PersonnelFormDTO registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        AccountUser accountUser = personnelService.registerPerson(currentUser, request);

        shortcutService.assignDefaultShortcut(accountUser.getId());

        return personnelMapper.mapToPersonnelFormDTO(accountUser);
    }

    public PersonnelFormDTO updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        AccountUser accountUser = personnelService.updatePerson(currentUser, request);

        return personnelMapper.mapToPersonnelFormDTO(accountUser);
    }

    public Page<PersonnelFormDTO> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<AccountUser> personnel = personnelService.getAllPersonnel(currentUser, pageable);

        return personnelMapper.mapToPersonnelFormDTO(personnel);
    }

    public ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        return personnelService.deletePersonById(currentUser, personId);
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds) {
        return personnelService.deleteAllByIds(currentUser, personnelIds);
    }

    public PersonnelFormDTO getPersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        AccountUser person = personnelService.getPersonById(currentUser, personId);

        return personnelMapper.mapToPersonnelFormDTO(person);
    }
}
