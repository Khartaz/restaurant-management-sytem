package com.restaurant.management.service.ecommerce;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonnelService {

    User registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request);

    User updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request);

    Page<User> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId);

    ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds);

    User getPersonById(@CurrentUser UserPrincipal currentUser, Long personId);

}
