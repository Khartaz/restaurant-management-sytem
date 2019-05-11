package com.restaurant.management.service.facade;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
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

    public DailyOrderListDto openOrderList() {
        DailyOrderList dailyOrderList = dailyOrderListService.openOrderList();

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public Page<DailyOrderListDto> getAll(Pageable pageable) {
        Page<DailyOrderList> dailyOrderLists = dailyOrderListService.getAll(pageable);

        return dailyOrderListMapper.mapToDailyOrderListDtoPage(dailyOrderLists);
    }

    public DailyOrderListDto getOrderListByUniqueId(String uniqueId) {
        DailyOrderList dailyOrderList = dailyOrderListService.getOrderListByUniqueId(uniqueId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public ApiResponse deleteByUniqueId(String uniqueId) {
        return dailyOrderListService.deleteByUniqueId(uniqueId);
    }

    public DailyOrderListDto addOrderToList(Long orderId) {
        DailyOrderList dailyOrderList = dailyOrderListService.addOrderToList(orderId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto removeOrderFromList(Long orderId) {
        DailyOrderList dailyOrderList = dailyOrderListService.removeOrderFromList(orderId);

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto closeDailyList() {
        DailyOrderList dailyOrderList = dailyOrderListService.closeDailyList();

        return dailyOrderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }
}
