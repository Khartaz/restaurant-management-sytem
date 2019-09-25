package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.PersonnelFormDTO;
import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.exception.ecommerce.user.UserAuthenticationException;
import com.restaurant.management.exception.ecommerce.user.UserMessages;
import com.restaurant.management.exception.ecommerce.user.UserNotFoundException;
import com.restaurant.management.repository.ecommerce.UserRepository;
import com.restaurant.management.repository.ecommerce.RoleRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.service.ecommerce.UserService;
import com.restaurant.management.service.ecommerce.LayoutSettingsService;
import com.restaurant.management.service.ecommerce.PersonnelService;
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

import static com.restaurant.management.utils.Validation.validatePhoneNumberFormat;

@Service
@Transactional
public class PersonnelServiceImpl implements PersonnelService {

    private UserRepository userRepository;
    private UserService userService;
    private RoleRepository roleRepository;
    private JwtTokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;
    private LayoutSettingsService settingsService;


    @Autowired
    public PersonnelServiceImpl(UserRepository userRepository,
                                UserService userService,
                                RoleRepository roleRepository,
                                JwtTokenProvider tokenProvider,
                                PasswordEncoder passwordEncoder,
                                LayoutSettingsService settingsService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;
    }

    public User registerPerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        userService.checkEmailAvailabilityInCompany(currentUser, request.getEmail());

        if (!request.getPhone().isEmpty()) {
            validatePhoneNumberFormat(request.getPhone());
        }

        Company company = userService.getCompany(currentUser);

        Settings settings = settingsService.createDefaultLayoutSettings();

        String companyName = company.getName();
        String withoutWhiteSpacesLowerCaseCompanyName = companyName.replaceAll("\\s+","").toLowerCase();

        UserAddress userAddress = new UserAddress();
        Stream.of(userAddress)
                .forEach(a -> {
                    a.setStreetAndNumber(request.getStreetAndNumber());
                    a.setPostCode(request.getPostCode());
                    a.setCity(request.getCity());
                    a.setCountry(request.getCountry());
                });

        String token = tokenProvider.generateEmailVerificationToken(request.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        User person = new User();
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
                    p.setUserAddress(userAddress);
                });

        userRepository.save(person);

        return person;
    }

    public User updatePerson(@CurrentUser UserPrincipal currentUser, PersonnelFormDTO request) {
        User person = userService.getCompanyUserById(currentUser, request.getId());

        if (!person.getEmail().equals(request.getEmail())) {
            userService.checkEmailAvailabilityInCompany(currentUser, request.getEmail());
        }

        if (!request.getPhone().isEmpty()) {
            validatePhoneNumberFormat(request.getPhone());
        }

        Stream.of(person)
                .forEach(p -> {
                    p.setName(request.getName());
                    p.setLastName(request.getLastName());
                    p.setEmail(request.getEmail());
                    p.setPhone(request.getPhone());
                    p.setJobTitle(request.getJobTitle());
                    p.setActive(request.getActive());
                    p.getUserAddress().setStreetAndNumber(request.getStreetAndNumber());
                    p.getUserAddress().setPostCode(request.getPostCode());
                    p.getUserAddress().setCity(request.getCity());
                    p.getUserAddress().setCountry(request.getCountry());

                    userRepository.save(p);
                });

        return person;
    }

    public Page<User> getAllPersonnel(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Company company = userService.getCompany(currentUser);

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        User manager = userRepository.findByRolesAndCompanyAndIsDeletedIsFalse(Collections.singleton(userRole), company)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage()));

        Page<User> accountUsers = userRepository.findAllByCompanyAndIsDeletedIsFalse(company, pageable);

        List<User> content = accountUsers.getContent()
                .stream()
                .filter(au -> !au.getId().equals(manager.getId()))
                .collect(Collectors.toList());

        return new PageImpl<>(content);
    }

    public ApiResponse deletePersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        User person = userService.getCompanyUserById(currentUser, personId);

        person.setDeleted(Boolean.TRUE);
        userRepository.save(person);

        return new ApiResponse(true, UserMessages.ACCOUNT_DELETED.getMessage());
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] personnelIds) {
        List<Long> ids = new ArrayList<>(Arrays.asList(personnelIds));

        List<User> personnel = userRepository.findAllByIdIn(ids);
        Stream.of(personnel)
                .forEach(p -> {
                    p.iterator().forEachRemaining(v -> v.setDeleted(Boolean.TRUE));
                    p.iterator().forEachRemaining(v -> userRepository.save(v));
                });

        return new ApiResponse(true, UserMessages.ACCOUNTS_DELETED.getMessage());
    }

    public User getPersonById(@CurrentUser UserPrincipal currentUser, Long personId) {
        Long companyId = userService.getCompany(currentUser).getId();

        return userRepository.findByIdAndCompanyIdAndIsDeletedIsFalse(personId, companyId)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.USER_NOT_FOUND.getMessage()));
    }

}
