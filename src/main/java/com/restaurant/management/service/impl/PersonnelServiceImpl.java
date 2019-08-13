package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.PersonnelService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class PersonnelServiceImpl implements PersonnelService {

    private AccountUserRepository accountUserRepository;
    private AccountUserService accountUserService;
    private RoleRepository roleRepository;
    private JwtTokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;
    private LayoutSettingsService settingsService;


    @Autowired
    public PersonnelServiceImpl(AccountUserRepository accountUserRepository,
                                AccountUserService accountUserService,
                                RoleRepository roleRepository,
                                JwtTokenProvider tokenProvider,
                                PasswordEncoder passwordEncoder,
                                LayoutSettingsService settingsService) {
        this.accountUserRepository = accountUserRepository;
        this.accountUserService = accountUserService;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;
    }

    public AccountUser registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        accountUserService.checkEmailAvailability(request.getEmail());

        Company company = accountUserService.getCompany(currentUser);

        Settings settings = settingsService.createDefaultLayoutSettings();

        String companyName = company.getName();
        String withoutWhiteSpacesLowerCaseCompanyName = companyName.replaceAll("\\s+","").toLowerCase();

        AccountUserAddress accountUserAddress = new AccountUserAddress();
        Stream.of(accountUserAddress)
                .forEach(a -> {
                    a.setStreetAndNumber(request.getStreetAndNumber());
                    a.setPostCode(request.getPostCode());
                    a.setCity(request.getCity());
                    a.setCountry(request.getCountry());
                });

        String token = tokenProvider.generateEmailVerificationToken(request.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        AccountUser person = new AccountUser();
        Stream.of(person)
                .forEach(p -> {
                    p.setName(request.getName());
                    p.setLastName(request.getLastName());
                    p.setEmail(request.getEmail());
                    p.setPhone(request.getPhone());
                    p.setJobTitle(request.getJobTitle());
                    p.setCompany(company);
                    p.setSettings(settings);
                    p.setDeleted(Boolean.FALSE);
                    p.setActive(Boolean.TRUE);
                    p.setEmailVerificationToken(token);
                    p.setPassword(passwordEncoder.encode(withoutWhiteSpacesLowerCaseCompanyName));
                    p.setRoles(Collections.singleton(userRole));
                    p.setAccountUserAddress(accountUserAddress);
                });

        accountUserRepository.save(person);

        return person;
    }

    public AccountUser updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        AccountUser person = accountUserService.getCompanyUserById(currentUser, request.getId());

        Stream.of(person)
                .forEach(p -> {
                    p.setName(request.getName());
                    p.setLastName(request.getLastName());
                    p.setEmail(request.getEmail());
                    p.setPhone(request.getPhone());
                    p.setJobTitle(request.getJobTitle());
                    p.setActive(request.getActive());
                    p.getAccountUserAddress().setStreetAndNumber(request.getStreetAndNumber());
                    p.getAccountUserAddress().setPostCode(request.getPostCode());
                    p.getAccountUserAddress().setCity(request.getCity());
                    p.getAccountUserAddress().setCity(request.getCountry());

                    accountUserRepository.save(p);
                });

        return person;
    }

    public Page<AccountUser> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Company company = accountUserService.getCompany(currentUser);

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        AccountUser manager = accountUserRepository.findByRolesAndCompanyAndIsDeletedIsFalse(Collections.singleton(userRole), company)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage()));

        Page<AccountUser> accountUsers = accountUserRepository.findAllByCompanyAndIsDeletedIsFalse(company, pageable);

        List<AccountUser> content = accountUsers.getContent()
                .stream()
                .filter(au -> !au.getId().equals(manager.getId()))
                .collect(Collectors.toList());

        return new PageImpl<>(content);
    }

    public ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        AccountUser person = accountUserService.getCompanyUserById(currentUser, personId);

        person.setDeleted(Boolean.TRUE);
        accountUserRepository.save(person);

        return new ApiResponse(true, UserMessages.ACCOUNT_DELETED.getMessage());
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds) {
        List<Long> ids = new ArrayList<>(Arrays.asList(personnelIds));

        List<AccountUser> personnel = accountUserRepository.findAllByIdIn(ids);
        Stream.of(personnel)
                .forEach(p -> {
                    p.iterator().forEachRemaining(v -> v.setDeleted(Boolean.TRUE));
                    p.iterator().forEachRemaining(v -> accountUserRepository.save(v));
                });

        return new ApiResponse(true, UserMessages.ACCOUNTS_DELETED.getMessage());
    }

    public AccountUser getPersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        Long companyId = accountUserService.getCompany(currentUser).getId();

        return accountUserRepository.findByIdAndCompanyId(personId, companyId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage()));
    }

}
