package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.service.facade.DailyOrderListFacade;
import com.restaurant.management.web.response.DailyOrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders/daily")
public class DailyOrderListController {

    private DailyOrderListFacade dailyOrderListFacade;
    private DailyOrderListMapper orderListMapper;

    @Autowired
    public DailyOrderListController(DailyOrderListFacade dailyOrderListFacade,
                                    DailyOrderListMapper orderListMapper) {
        this.dailyOrderListFacade = dailyOrderListFacade;
        this.orderListMapper = orderListMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> registerDailyOrderList() {
        DailyOrderListDto orderList = dailyOrderListFacade.openOrderList();

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<DailyOrderListResponse>> showOrdersList(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<DailyOrderListDto> dailyOrderList = dailyOrderListFacade.getAll(pageable);

        Page<DailyOrderListResponse> response = orderListMapper.mapToDailyOrderListResponsePage(dailyOrderList);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{uniqueId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> showOrderList(@PathVariable String uniqueId) {
        DailyOrderListDto dailyOrderList = dailyOrderListFacade.getOrderListByUniqueId(uniqueId);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(dailyOrderList);

        Link link = linkTo(DailyOrderListController.class).slash(dailyOrderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<?> deleteListByUniqueId(@PathVariable String uniqueId) {
        return ResponseEntity.ok().body(dailyOrderListFacade.deleteByUniqueId(uniqueId));
    }

    @PatchMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> addOrder(@RequestBody String orderNumber) {
        DailyOrderListDto orderList = dailyOrderListFacade.addOrderToList(orderNumber);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/remove",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> removeOrder(@RequestBody String orderNumber) {
        DailyOrderListDto orderList = dailyOrderListFacade.removeOrderFromList(orderNumber);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/close",
            consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> closeDailyList() {
        DailyOrderListDto orderList = dailyOrderListFacade.closeDailyList();

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getUniqueId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
