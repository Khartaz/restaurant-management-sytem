package com.restaurant.management.service.facade;

import com.restaurant.management.domain.ecommerce.DailyOrderList;
import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.restaurant.StatisticsReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class DailyOrderListFacade {

    private DailyOrderListService dailyOrderListService;
    private DailyOrderListMapper dailyOrderListMapper;

    @Autowired
    public DailyOrderListFacade(DailyOrderListService dailyOrderListService, DailyOrderListMapper dailyOrderListMapper) {
        this.dailyOrderListService = dailyOrderListService;
        this.dailyOrderListMapper = dailyOrderListMapper;
    }

    public Page<DailyOrderListDto> getAll(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<DailyOrderList> dailyOrderLists = dailyOrderListService.getAll(currentUser, pageable);

        return dailyOrderListMapper.mapToDailyOrderListDtoPage(dailyOrderLists);
    }

    public DailyOrderListDto getOrderListById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        DailyOrderList dailyOrderList = dailyOrderListService.getOrderListById(currentUser, orderListId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public ApiResponse deleteById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        return dailyOrderListService.deleteById(currentUser, orderListId);
    }

    public DailyOrderListDto addOrderToList(@CurrentUser UserPrincipal currentUser, Long orderId) {
        DailyOrderList dailyOrderList = dailyOrderListService.addOrderToList(currentUser, orderId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto removeOrderFromList(@CurrentUser UserPrincipal currentUser, Long orderId) {
        DailyOrderList dailyOrderList = dailyOrderListService.removeOrderFromList(currentUser, orderId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto closeDailyList(@CurrentUser UserPrincipal currentUser) {
        DailyOrderList dailyOrderList = dailyOrderListService.closeDailyList(currentUser);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public StatisticsReportResponse countDailyOrders(@CurrentUser UserPrincipal currentUser) {
        return dailyOrderListService.countDailyOrders(currentUser);
    }
}
