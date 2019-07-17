package com.restaurant.management.service.impl;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.ecommerce.DailyOrderList;
import com.restaurant.management.domain.ecommerce.Order;
import com.restaurant.management.exception.order.OrderListExistsException;
import com.restaurant.management.exception.order.OrderListNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.DailyOrderListRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.restaurant.StatisticsReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@SuppressWarnings("Duplicates")
public class DailyOrderListServiceImpl implements DailyOrderListService {
    private DailyOrderListRepository dailyOrderListRepository;
    private OrderRepository orderRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public DailyOrderListServiceImpl(DailyOrderListRepository dailyOrderListRepository,
                                     OrderRepository orderRepository,
                                     AccountUserRepository accountUserRepository) {
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.orderRepository = orderRepository;
        this.accountUserRepository = accountUserRepository;
    }

    private AccountUser getUser(@CurrentUser UserPrincipal currentUser) {
        return accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private Order getOrderByIdAndRestaurantId(Long orderId, Long restaurantId) {
        return orderRepository.findByIdAndRestaurantInfoId(orderId, restaurantId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));
    }

    public DailyOrderList getOrderListById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return dailyOrderListRepository.findByIdAndRestaurantInfoId(orderListId, restaurantId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public Page<DailyOrderList> getAll(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = getUser(currentUser);

        return dailyOrderListRepository.findAllByRestaurantInfoId(accountUser.getRestaurantInfo().getId(), pageable);
    }

    public boolean checkDailyOrderListExists(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUser(currentUser);

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return dailyOrderListRepository.existsByIsOpenTrueAndRestaurantInfoId(restaurantId);
    }

    public boolean openOrderList(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUser(currentUser);

        DailyOrderList orderList = new DailyOrderList.DailyOrderListBuilder()
                .setDailyIncome(0.00)
                .setNumberOfOrders(0)
                .setIsOpen(Boolean.TRUE)
                .setOrders(new LinkedHashSet<>())
                .setRestaurantInfo(accountUser.getRestaurantInfo())
                .build();

        dailyOrderListRepository.save(orderList);

        return true;
    }

    public DailyOrderList getOpenedOrderList(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return dailyOrderListRepository.findDailyOrderListByIsOpenTrueAndRestaurantInfoId(restaurantId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public DailyOrderList addOrderToList(@CurrentUser UserPrincipal currentUser, Long orderId) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        Order order = getOrderByIdAndRestaurantId(orderId, restaurantId);

        DailyOrderList dailyOrderList = getOpenedOrderList(currentUser);

        Optional<Order> result = getOptionalOrder(dailyOrderList, orderId);

        if (result.isPresent()) {
            throw new OrderListExistsException(OrderMessages.ORDER_EXISTS_ON_LIST.getMessage());
        }

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.add(order);

        double income = order.getCart().getTotalPrice() + dailyOrderList.getDailyIncome();
        income = Math.floor(income * 100) / 100;

        dailyOrderList.setDailyIncome(income);
        dailyOrderList.setNumberOfOrders(orders.size());
        dailyOrderList.setOrders(orders);

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    private Optional<Order> getOptionalOrder(DailyOrderList dailyOrderList, Long orderId) {
        return dailyOrderList.getOrders().stream()
                .filter(v -> v.getId().equals(orderId))
                .findAny();
    }

    public DailyOrderList removeOrderFromList(@CurrentUser UserPrincipal currentUser, Long orderId) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        Order order = getOrderByIdAndRestaurantId(orderId, restaurantId);

        DailyOrderList dailyOrderList = getOpenedOrderList(currentUser);

        Optional<Order> result = getOptionalOrder(dailyOrderList, orderId);

        if (!result.isPresent()) {
            throw new OrderListExistsException(OrderMessages.ORDER_NO_EXISTS_ON_LIST.getMessage());
        }

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.remove(order);

        double income = dailyOrderList.getDailyIncome() - order.getCart().getTotalPrice();
        income = Math.floor(income * 100) / 100;

        dailyOrderList.setDailyIncome(income);
        dailyOrderList.setNumberOfOrders(orders.size());
        dailyOrderList.setOrders(orders);

        if (dailyOrderList.getOrders().size() == 0) {
            dailyOrderList.setDailyIncome(0.0);
        }

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public DailyOrderList closeDailyList(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        DailyOrderList dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenTrueAndRestaurantInfoId(restaurantId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_OPEN.getMessage()));

        dailyOrderList.setIsOpen(Boolean.FALSE);

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public ApiResponse deleteById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        DailyOrderList orderList = dailyOrderListRepository.findByIdAndRestaurantInfoId(orderListId, restaurantId)
                .orElseThrow(() -> new OrderListExistsException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));

        dailyOrderListRepository.delete(orderList);

        return new ApiResponse(true, OrderMessages.ORDER_LIST_DELETED.getMessage());
    }

    public StatisticsReportResponse countDailyOrders(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = getUser(currentUser);
        Long restaurantId = accountUser.getRestaurantInfo().getId();

        DailyOrderList dailyOrderList = dailyOrderListRepository.findByIsOpenIsTrueAndRestaurantInfoId(restaurantId);

        Integer productsCount = dailyOrderList.getOrders().stream()
                .mapToInt(v -> v.getCart()
                        .getLineItems()
                        .size())
                .sum();

        return new StatisticsReportResponse(
                dailyOrderList.getNumberOfOrders(),
                dailyOrderList.getDailyIncome(),
                productsCount
        );
    }

}
