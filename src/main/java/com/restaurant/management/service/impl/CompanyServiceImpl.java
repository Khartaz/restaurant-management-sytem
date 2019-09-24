package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.CompanyFormDTO;
import com.restaurant.management.exception.company.CompanyMessages;
import com.restaurant.management.exception.company.CompanyNotFoundException;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.ecommerce.AccountUserRepository;
import com.restaurant.management.repository.ecommerce.CompanyRepository;
import com.restaurant.management.repository.ecommerce.RoleRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.CompanyService;
import com.restaurant.management.web.request.user.SignUpUserRequest;
import com.restaurant.management.web.request.company.RegisterCompanyRequest;
import com.restaurant.management.web.request.company.CompanyRequest;
import com.restaurant.management.web.response.company.RegisterCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.stream.Stream;

import static com.restaurant.management.utils.Validation.validatePhoneNumberFormat;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private AccountUserRepository accountUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private LayoutSettingsService settingsService;
    private AccountUserService accountUserService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              AccountUserRepository accountUserRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder,
                              LayoutSettingsService settingsService,
                              AccountUserService accountUserService) {
        this.companyRepository = companyRepository;
        this.accountUserRepository = accountUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;
        this.accountUserService = accountUserService;

    }

    public RegisterCompany registerCompany(RegisterCompanyRequest request) {

        Company company = registerCompany(request.getCompanyRequest());

        AccountUser accountUser = registerCompanyManager(request.getSignUpUserRequest());

        accountUser.setCompany(company); // <-- Assign company to user

        accountUserRepository.save(accountUser);
        companyRepository.save(company);

        return new RegisterCompany(accountUser, company);
    }

    public Company updateCompanyInfo(@CurrentUser UserPrincipal currentUser, CompanyFormDTO request) {
        Company company = getCompanyById(currentUser);

        if (!request.getPhone().isEmpty()) {
            validatePhoneNumberFormat(request.getPhone());
        }

        Stream.of(company)
                .forEach(c -> {
                    c.setName(request.getName());
                    c.setPhone(request.getPhone());
                    c.getCompanyAddress().setStreetAndNumber(request.getStreetAndNumber());
                    c.getCompanyAddress().setPostCode(request.getPostCode());
                    c.getCompanyAddress().setCity(request.getCity());
                    c.getCompanyAddress().setCountry(request.getCountry());
                });

        companyRepository.save(company);

        return company;
    }

    public Company getCompanyById(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUserById(currentUser.getId());

        Long restaurantId = accountUser.getCompany().getId();

        return companyRepository.findById(restaurantId)
                .orElseThrow(() -> new CompanyNotFoundException(CompanyMessages.RESTAURANT_NOT_FOUND.getMessage()));
    }

    private AccountUser getUserById(Long id) {
        return accountUserRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    private Company registerCompany(CompanyRequest request) {

        Company company = new Company();
        company.setName(request.getCompanyName());
        company.setCompanyAddress(new CompanyAddress());

        return company;
    }

    private AccountUser registerCompanyManager(SignUpUserRequest request) {
        validatePhoneNumberFormat(request.getPhone());

        accountUserService.checkEmailAvailability(request.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        /**
         *  Temporary changed Active to TRUE and EmailVerificationToken to NULL
         *  Disabled email sending
         *  Change it back to production version
         */

//        simpleEmailService.sendEmailVerification(
//                new Mail(request.getEmail(), request.getName()), token);

        AccountUser accountUser = new AccountUser();
        Stream.of(accountUser)
                .forEach(user -> {
                    user.setName(request.getName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPhone(request.getPhone());
                    user.setJobTitle("Manager");
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setActive(Boolean.TRUE);
                    user.setDeleted(Boolean.FALSE);
                    user.setRoles(Collections.singleton(userRole));
                    user.setEmailVerificationToken(null);
                    user.setSettings(settingsService.createDefaultLayoutSettings());
                    user.setAccountUserAddress(new AccountUserAddress());
                });

        return accountUser;
    }
}
