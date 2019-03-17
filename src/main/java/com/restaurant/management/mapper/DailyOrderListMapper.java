package com.restaurant.management.mapper;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.web.response.DailyOrderListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyOrderListMapper {
    private OrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public DailyOrderList mapToDailyOrderList(final DailyOrderListDto orderListDto) {
        return new DailyOrderList(
                orderListDto.getId(),
                orderListDto.getUniqueId(),
                orderListDto.getDailyIncome(),
                orderListDto.getOpened(),
                orderListDto.getOrdersDto().stream()
                    .map(v -> orderMapper.mapToOrder(v))
                .collect(Collectors.toSet())
        );
    }

    public DailyOrderListDto mapToDailyOrderListDto(final DailyOrderList orderList) {
        return new DailyOrderListDto(
                orderList.getId(),
                orderList.getUniqueId(),
                orderList.getDailyIncome(),
                orderList.getOpened(),
                orderList.getOrders().stream()
                        .map(v -> orderMapper.mapToOrderDto(v))
                        .collect(Collectors.toSet())
        );
    }

    public DailyOrderListResponse mapToDailyOrderListResponse(final DailyOrderListDto orderListDto) {
        return new DailyOrderListResponse(
                orderListDto.getId(),
                orderListDto.getUniqueId(),
                orderListDto.getDailyIncome(),
                orderListDto.getOpened(),
                orderListDto.getOrdersDto().stream()
                        .map(v -> orderMapper.mapToOrderResponse(v))
                        .collect(Collectors.toSet())
        );
    }

    public List<DailyOrderList> mapToDailyOrderList(final List<DailyOrderListDto> orderListDto) {
        return orderListDto.stream()
                .map(this::mapToDailyOrderList)
                .collect(Collectors.toList());
    }

    public List<DailyOrderListDto> mapToDailyOrderListDto(final List<DailyOrderList> orderLists) {
        return orderLists.stream()
                .map(this::mapToDailyOrderListDto)
                .collect(Collectors.toList());
    }

    public List<DailyOrderListResponse> mapToDailyOrderListResponse(final List<DailyOrderListDto> orderListDto) {
        return orderListDto.stream()
                .map(this::mapToDailyOrderListResponse)
                .collect(Collectors.toList());
    }
}