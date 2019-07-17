package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
import com.restaurant.management.domain.ecommerce.RestaurantAddress;
import com.restaurant.management.domain.ecommerce.RestaurantInfo;
import com.restaurant.management.domain.layout.Settings;
import com.restaurant.management.exception.restaurant.RestaurantMessages;
import com.restaurant.management.exception.restaurant.RestaurantNotFoundException;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.RestaurantInfoRepository;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.LayoutSettingsService;
import com.restaurant.management.service.RestaurantInfoService;
import com.restaurant.management.web.request.user.SignUpUserRequest;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.request.restaurant.RestaurantInfoRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.restaurant.RegisterRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

import static com.restaurant.management.utils.Validation.validatePhoneNumber;

@Service
@Transactional
//@SuppressWarnings("Duplicates")
public class RestaurantInfoServiceImpl implements RestaurantInfoService {

    private RestaurantInfoRepository restaurantInfoRepository;
    private AccountUserRepository accountUserRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private LayoutSettingsService settingsService;

    @Autowired
    public RestaurantInfoServiceImpl(RestaurantInfoRepository restaurantInfoRepository,
                                     AccountUserRepository accountUserRepository,
                                     RoleRepository roleRepository,
                                     PasswordEncoder passwordEncoder,
                                     LayoutSettingsService settingsService) {
        this.restaurantInfoRepository = restaurantInfoRepository;
        this.accountUserRepository = accountUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.settingsService = settingsService;

    }

    public RegisterRestaurant registerRestaurant(RegisterRestaurantRequest request) {
        Settings settings = settingsService.createDefaultLayoutSettings();


        RestaurantInfo restaurantInfo = registerRestaurantInfo(request.getRestaurantInfoRequest());

        AccountUser accountUser = registerRestaurantManager(request.getSignUpUserRequest());

        accountUser.setRestaurantInfo(restaurantInfo); // <-- Assign restaurant to user
        accountUser.setSettings(settings);            //  <-- Assign default layout settings

        accountUserRepository.save(accountUser);
        restaurantInfoRepository.save(restaurantInfo);

        return new RegisterRestaurant(accountUser, restaurantInfo);
    }

    private ApiResponse checkEmailAvailability(String email) {
        if(accountUserRepository.existsByEmail(email)) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    private AccountUser registerRestaurantManager(SignUpUserRequest request) {

        validatePhoneNumber(request.getPhoneNumber());

        checkEmailAvailability(request.getEmail());

        Role userRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                .orElseThrow(() -> new UserAuthenticationException(UserMessages.ROLE_NOT_SET.getMessage()));

        /**
         *  Temporary changed Active to TRUE and EmailVerificationToken to NULL
         *  Disabled email sending
         *  Change it back to production version
         */

//        simpleEmailService.sendEmailVerification(
//                new Mail(request.getEmail(), request.getName()), token);

        return new AccountUser.AccountUserBuilder()
                .setName(request.getName())
                .setLastname(request.getLastname())
                .setEmail(request.getEmail())
                .setPhoneNumber(request.getPhoneNumber())
                .setPassword(passwordEncoder.encode(request.getPassword()))
                .setIsActive(Boolean.TRUE)
                .setRoles(Collections.singleton(userRole))
                .setEmailVerificationToken(null)
                .setSettings(settingsService.createDefaultLayoutSettings())
                .setUserAddress(registerUserAddress())
                .build();
    }

    public RestaurantInfo getRestaurantInfoById(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUserById(currentUser.getId());

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return restaurantInfoRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException(RestaurantMessages.RESTAURANT_NOT_FOUND.getMessage()));
    }

    private AccountUser getUserById(Long id) {
        return accountUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage() + id));
    }

    private AccountUserAddress registerUserAddress() {
        return new AccountUserAddress.AccountUserAddressBuilder()
                .setCity("Update City")
                .setCountry("Update Country")
                .setPostCode("Update post code")
                .setStreetAndNumber("Update street and number")
                .build();
    }

    private RestaurantInfo registerRestaurantInfo(RestaurantInfoRequest request) {

        RestaurantAddress address = new RestaurantAddress.RestaurantAddressBuilder()
                .setCity("Update City")
                .setCountry("Update Country")
                .setPostCode("Update post code")
                .setStreetAndNumber("Update street and number")
                .build();

        RestaurantInfo restaurantInfo = new RestaurantInfo();
        restaurantInfo.setName(request.getRestaurantName());
        restaurantInfo.setRestaurantAddress(address);

        return restaurantInfo;
    }
}
