package com.restaurant.management.service;

import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.domain.dto.DailyOrderListDto;
import com.restaurant.management.exception.order.OrderListExistsException;
import com.restaurant.management.exception.order.OrderListNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.mapper.DailyOrderListMapper;
import com.restaurant.management.repository.DailyOrderListRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class DailyOrderListService {
    private DailyOrderListRepository dailyOrderListRepository;
    private OrderRepository orderRepository;
    private Utils utils;
    private DailyOrderListMapper orderListMapper;

    @Autowired
    public DailyOrderListService(DailyOrderListRepository dailyOrderListRepository,
                                 OrderRepository orderRepository,
                                 Utils utils,
                                 DailyOrderListMapper orderListMapper) {
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.orderRepository = orderRepository;
        this.utils = utils;
        this.orderListMapper = orderListMapper;
    }

    public DailyOrderListDto getOrderListByUniqueId(String uniqueId) {
        DailyOrderList dailyOrderList = dailyOrderListRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public List<DailyOrderListDto> getAll() {
        List<DailyOrderList> dailyOrderList = dailyOrderListRepository.findAll();

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto openOrderList() {
        if (dailyOrderListRepository.existsByIsOpenedTrue()) {
            throw new OrderListExistsException(OrderMessages.ORDER_LIST_EXISTS.getMessage());
        }

        DailyOrderList orderList = new DailyOrderList();

        orderList.setUniqueId(utils.generateDailyOrderListUniqueId(10));
        orderList.setDailyIncome(0.00);
        orderList.setOpened(Boolean.TRUE);
        orderList.setOrders(new LinkedHashSet<>());

        dailyOrderListRepository.save(orderList);

        return orderListMapper.mapToDailyOrderListDto(orderList);
    }

    public DailyOrderListDto getOpenedOrderList() {
        DailyOrderList dailyOrderList =  dailyOrderListRepository.findDailyOrderListByIsOpenedTrue()
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto addOrderToList(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = orderListMapper.mapToDailyOrderList(getOpenedOrderList());

        int oldSize = dailyOrderList.getOrders().size();

        dailyOrderList.getOrders().add(order);

        if (dailyOrderList.getOrders().size() > oldSize) {
            double income = order.getTotalPrice() + dailyOrderList.getDailyIncome();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
        }
        dailyOrderListRepository.save(dailyOrderList);

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto removeOrderFromList(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_NUMBER_NOT_FOUND.getMessage()));

        DailyOrderList dailyOrderList = orderListMapper.mapToDailyOrderList(getOpenedOrderList());

        int oldSize = dailyOrderList.getOrders().size();

        dailyOrderList.getOrders().remove(order);

        if (dailyOrderList.getOrders().size() < oldSize) {
            double income = dailyOrderList.getDailyIncome() - order.getTotalPrice();
            income = Math.floor(income * 100) / 100;
            dailyOrderList.setDailyIncome(income);
        }
        dailyOrderListRepository.save(dailyOrderList);

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public DailyOrderListDto closeDailyList() {
        DailyOrderList dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenedTrue()
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_OPEN.getMessage()));

        dailyOrderList.setOpened(Boolean.FALSE);
        dailyOrderListRepository.save(dailyOrderList);

        return orderListMapper.mapToDailyOrderListDto(dailyOrderList);
    }

    public void deleteByUniqueId(String uniqueId) {
        Optional<DailyOrderList> orderList = dailyOrderListRepository.findByUniqueId(uniqueId);

        if (orderList.isPresent()) {
            dailyOrderListRepository.delete(orderList.get());
        } else {
            throw new OrderListExistsException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage());
        }
    }

}
