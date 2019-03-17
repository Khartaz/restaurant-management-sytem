package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.DailyOrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders/daily")
public class DailyOrderListController {

    private DailyOrderListService orderListService;
    private DailyOrderListMapper orderListMapper;

    @Autowired
    public DailyOrderListController(DailyOrderListService orderListService,
                                    DailyOrderListMapper orderListMapper) {
        this.orderListService = orderListService;
        this.orderListMapper = orderListMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> registerDailyOrderList() {
        DailyOrderListDto orderList = orderListService.openOrderList();

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resources<DailyOrderListResponse> showOrderLists() {
        List<DailyOrderListDto> dailyOrderList = orderListService.getAll();

        List<DailyOrderListResponse> response = orderListMapper.mapToDailyOrderListResponse(dailyOrderList);

        Link link = linkTo(DailyOrderListController.class).withSelfRel();

        return new Resources<>(response, link);
    }

    @GetMapping(value = "/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> showOrderList(@PathVariable String uniqueId) {
        DailyOrderListDto dailyOrderList = orderListService.getOrderListByUniqueId(uniqueId);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(dailyOrderList);

        Link link = linkTo(DailyOrderListController.class).slash(dailyOrderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<?> deleteListByUniqueId(@PathVariable String uniqueId) {
        orderListService.deleteByUniqueId(uniqueId);
        return ResponseEntity.ok().body(new ApiResponse(true, OrderMessages.ORDER_LIST_DELETED.getMessage()));
    }

    @PatchMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> addOrder(@RequestBody String orderNumber) {
        DailyOrderListDto orderList = orderListService.addOrderToList(orderNumber);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/remove",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> removeOrder(@RequestBody String orderNumber) {
        DailyOrderListDto orderList = orderListService.removeOrderFromList(orderNumber);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/close",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> closeDailyList() {
        DailyOrderListDto orderList = orderListService.closeDailyList();

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
