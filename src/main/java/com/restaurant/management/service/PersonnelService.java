package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.AccountUser;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonnelService {

    AccountUser registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request);

    AccountUser updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request);

    Page<AccountUser> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId);

    ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds);

    AccountUser getPersonById(@CurrentUser UserPrincipal currentUser, Long personId);

}
