package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.restaurant.RestaurantMessages;
import com.restaurant.management.exception.restaurant.RestaurantNotFoundException;
import com.restaurant.management.exception.user.UserAuthenticationException;
import com.restaurant.management.exception.user.UserExistsException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.mapper.AccountUserMapper;
import com.restaurant.management.mapper.RestaurantInfoMapper;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.RestaurantInfoRepository;
import com.restaurant.management.repository.RoleRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.security.jwt.JwtTokenProvider;
import com.restaurant.management.service.RestaurantInfoService;
import com.restaurant.management.service.SimpleEmailService;
import com.restaurant.management.web.request.account.SignUpUserRequest;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.request.restaurant.RestaurantInfoRequest;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.restaurant.RegisterRestaurantResponse;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;
import com.restaurant.management.web.response.user.AccountUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
//@SuppressWarnings("Duplicates")
public class RestaurantInfoServiceImpl implements RestaurantInfoService {

    private RestaurantInfoRepository restaurantInfoRepository;
    private AccountUserRepository accountUserRepository;
    private RoleRepository roleRepository;
    private JwtTokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder;
    private SimpleEmailService simpleEmailService;
    private AccountUserMapper accountUserMapper;
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    public RestaurantInfoServiceImpl(RestaurantInfoRepository restaurantInfoRepository,
                                     AccountUserRepository accountUserRepository,
                                     RoleRepository roleRepository,
                                     JwtTokenProvider tokenProvider,
                                     PasswordEncoder passwordEncoder,
                                     SimpleEmailService simpleEmailService,
                                     AccountUserMapper accountUserMapper,
                                     RestaurantInfoMapper restaurantInfoMapper) {
        this.restaurantInfoRepository = restaurantInfoRepository;
        this.accountUserRepository = accountUserRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.simpleEmailService = simpleEmailService;
        this.accountUserMapper = accountUserMapper;
        this.restaurantInfoMapper = restaurantInfoMapper;
    }

    public RegisterRestaurantResponse registerRestaurant(RegisterRestaurantRequest request) {

        RestaurantInfo restaurantInfo = registerRestaurantInfo(request.getRestaurantInfoRequest());

        AccountUser accountUser = registerRestaurantManager(request.getSignUpUserRequest());

        accountUser.setRestaurantInfo(restaurantInfo); // <-- Assign restaurant to user

        accountUserRepository.save(accountUser);
        restaurantInfoRepository.save(restaurantInfo);

        AccountUserResponse accountUserResponse = accountUserMapper.mapToAccountUserResponse(
                accountUserMapper.mapToAccountUserDto(accountUser)
        );

        RestaurantInfoResponse restaurantInfoResponse = restaurantInfoMapper.mapToRestaurantInfoResponse(restaurantInfo);

        return new RegisterRestaurantResponse(accountUserResponse, restaurantInfoResponse);
    }

    private RestaurantInfo registerRestaurantInfo(RestaurantInfoRequest request) {

        RestaurantAddress address = new RestaurantAddress.RestaurantAddressBuilder()
                .setCity(request.getRestaurantAddressRequest().getCity())
                .setCountry(request.getRestaurantAddressRequest().getCountry())
                .setPostCode(request.getRestaurantAddressRequest().getPostCode())
                .setStreetAndNumber(request.getRestaurantAddressRequest().getStreetAndNumber())
                .build();

        RestaurantInfo restaurantInfo = new RestaurantInfo();
                restaurantInfo.setName(request.getRestaurantName());
                restaurantInfo.setRestaurantAddress(address);

        return restaurantInfo;
    }

    private ApiResponse checkEmailAvailability(String email) {
        if(accountUserRepository.existsByEmail(email)) {
            throw new UserExistsException(UserMessages.EMAIL_TAKEN.getMessage());
        }
        return new ApiResponse(true, UserMessages.EMAIL_AVAILABLE.getMessage());
    }

    private AccountUser registerRestaurantManager(SignUpUserRequest request) {
        checkEmailAvailability(request.getEmail());

        String token = tokenProvider.generateEmailVerificationToken(request.getEmail());

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
}
