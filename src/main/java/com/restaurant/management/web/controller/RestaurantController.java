package com.restaurant.management.web.controller;

import com.restaurant.management.service.facade.RestaurantInfoAccountUserFacade;
import com.restaurant.management.web.request.restaurant.RegisterRestaurantRequest;
import com.restaurant.management.web.response.restaurant.RegisterRestaurantResponse;
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

    @Autowired
    public RestaurantController(RestaurantInfoAccountUserFacade restaurantInfoAccountUserFacade) {
        this.restaurantInfoAccountUserFacade = restaurantInfoAccountUserFacade;
    }

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<RegisterRestaurantResponse> registerRestaurant(@Valid @RequestBody RegisterRestaurantRequest request) {
        RegisterRestaurantResponse registerRestaurantResponse = restaurantInfoAccountUserFacade.registerRestaurant(request);

        Link link = linkTo(RestaurantController.class).withSelfRel();

        return new Resource<>(registerRestaurantResponse, link);
    }

}
