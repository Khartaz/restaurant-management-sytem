package com.restaurant.management.web.controller;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.service.DailyOrderListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders/daily")
public class DailyOrderListController {

    private DailyOrderListService orderListService;

    @Autowired
    public DailyOrderListController(DailyOrderListService orderListService) {
        this.orderListService = orderListService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderList> registerDailyOrderList() {
        DailyOrderList orderList = orderListService.openOrderList();

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(orderList, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<DailyOrderList> showOrderLists() {
        List<DailyOrderList> dailyOrderList = orderListService.getAll();

        Link link = linkTo(DailyOrderListController.class).withSelfRel();

        return new Resources<>(dailyOrderList, link);
    }

    @GetMapping(value = "/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderList> showOrderList(@PathVariable String uniqueId) {
        DailyOrderList dailyOrderList = orderListService.getOrderListByUniqueId(uniqueId);

        Link link = linkTo(DailyOrderListController.class).slash(dailyOrderList.getUniqueId()).withSelfRel();

        return new Resource<>(dailyOrderList, link);
    }

    @PatchMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderList> addOrder(@RequestBody String orderNumber) {
        DailyOrderList orderList = orderListService.addOrderToList(orderNumber);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(orderList, link);
    }


    @PatchMapping(value = "/remove",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderList> removeOrder(@RequestBody String orderNumber) {
        DailyOrderList orderList = orderListService.removeOrderFromList(orderNumber);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(orderList, link);
    }

    @PatchMapping(value = "/close",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderList> closeDay() {
        DailyOrderList orderList = orderListService.closeDailyList();

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(orderList, link);
    }


}
