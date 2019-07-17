package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.RestaurantInfoDto;
import com.restaurant.management.mapper.RestaurantInfoMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.facade.RestaurantInfoAccountUserFacade;
import com.restaurant.management.service.facade.RestaurantInfoFacade;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RestaurantInfoResponse;
import com.restaurant.management.web.response.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private RestaurantInfoAccountUserFacade restaurantInfoAccountUserFacade;
    private RestaurantInfoFacade restaurantInfoFacade;
    private RestaurantInfoMapper restaurantInfoMapper;

    @Autowired
    public RestaurantController(RestaurantInfoAccountUserFacade restaurantInfoAccountUserFacade,
                                RestaurantInfoFacade restaurantInfoFacade,
                                RestaurantInfoMapper restaurantInfoMapper) {
        this.restaurantInfoAccountUserFacade = restaurantInfoAccountUserFacade;
        this.restaurantInfoFacade = restaurantInfoFacade;
        this.restaurantInfoMapper = restaurantInfoMapper;
    }

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<UserResponse> registerRestaurant(@Valid @RequestBody RegisterRestaurantRequest request) {
        UserResponse registerLoginResponse = restaurantInfoAccountUserFacade.registerRestaurant(request);

        Link link = linkTo(RestaurantController.class).withSelfRel();

        return new Resource<>(registerLoginResponse, link);
    }

    @GetMapping(value = "/my", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<RestaurantInfoResponse> getRestaurantInfo(@CurrentUser UserPrincipal currentUser) {
        RestaurantInfoDto restaurantInfoDto = restaurantInfoFacade.getRestaurantInfo(currentUser);

        RestaurantInfoResponse response = restaurantInfoMapper.mapToRestaurantInfoResponse(restaurantInfoDto);

        Link link = linkTo(RestaurantController.class).slash("/my").withSelfRel();

        return new Resource<>(response, link);
    }

}
