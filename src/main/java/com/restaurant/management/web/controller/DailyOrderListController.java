package com.restaurant.management.web.controller;

import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
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

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> registerDailyOrderList(@CurrentUser UserPrincipal currentUser) {
        DailyOrderListDto orderList = dailyOrderListFacade.openOrderList(currentUser);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<PagedResources<DailyOrderListResponse>> showOrdersLists(@CurrentUser UserPrincipal currentUser,
                                                                          Pageable pageable, PagedResourcesAssembler assembler) {
        Page<DailyOrderListDto> dailyOrderList = dailyOrderListFacade.getAll(currentUser, pageable);

        Page<DailyOrderListResponse> response = orderListMapper.mapToDailyOrderListResponsePage(dailyOrderList);

        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }

    @GetMapping(value = "/{orderListId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> showOrderList(@CurrentUser UserPrincipal currentUser,
                                                   @PathVariable Long orderListId) {
        DailyOrderListDto dailyOrderList = dailyOrderListFacade.getOrderListById(currentUser, orderListId);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(dailyOrderList);

        Link link = linkTo(DailyOrderListController.class).slash(dailyOrderList.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @DeleteMapping(value = "/{orderListId}")
    public ResponseEntity<?> deleteListById(@CurrentUser UserPrincipal currentUser,
                                                  @PathVariable Long orderListId) {
        return ResponseEntity.ok().body(dailyOrderListFacade.deleteById(currentUser, orderListId));
    }

    @PatchMapping(value = "/add/{orderId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> addOrder(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable Long orderId) {
        DailyOrderListDto orderList = dailyOrderListFacade.addOrderToList(currentUser, orderId);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/remove/{orderId}", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> removeOrder(@CurrentUser UserPrincipal currentUser, @PathVariable Long orderId) {
        DailyOrderListDto orderList = dailyOrderListFacade.removeOrderFromList(currentUser, orderId);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

    @PatchMapping(value = "/close", produces = APPLICATION_JSON_VALUE)
    public @ResponseBody
    Resource<DailyOrderListResponse> closeDailyList(@CurrentUser UserPrincipal currentUser) {
        DailyOrderListDto orderList = dailyOrderListFacade.closeDailyList(currentUser);

        DailyOrderListResponse response = orderListMapper.mapToDailyOrderListResponse(orderList);

        Link link = linkTo(DailyOrderListController.class).slash(orderList.getId()).withSelfRel();

        return new Resource<>(response, link);
    }

}
