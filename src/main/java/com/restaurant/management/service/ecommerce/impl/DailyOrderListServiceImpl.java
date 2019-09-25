package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.User;
import com.restaurant.management.domain.ecommerce.DailyOrderList;
import com.restaurant.management.domain.ecommerce.Order;
import com.restaurant.management.exception.ecommerce.order.OrderListExistsException;
import com.restaurant.management.exception.ecommerce.order.OrderListNotFoundException;
import com.restaurant.management.exception.ecommerce.order.OrderMessages;
import com.restaurant.management.exception.ecommerce.order.OrderNotFoundException;
import com.restaurant.management.exception.ecommerce.user.UserMessages;
import com.restaurant.management.exception.ecommerce.user.UserNotFoundException;
import com.restaurant.management.repository.ecommerce.UserRepository;
import com.restaurant.management.repository.ecommerce.DailyOrderListRepository;
import com.restaurant.management.repository.ecommerce.OrderRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.DailyOrderListService;
import com.restaurant.management.web.response.ApiResponse;
import com.restaurant.management.web.response.company.StatisticsReportResponse;
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
    private UserRepository userRepository;

    @Autowired
    public DailyOrderListServiceImpl(DailyOrderListRepository dailyOrderListRepository,
                                     OrderRepository orderRepository,
                                     UserRepository userRepository) {
        this.dailyOrderListRepository = dailyOrderListRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    private User getUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }

    private Order getOrderByIdAndCompanyId(Long orderId, Long companyId) {
        return orderRepository.findByIdAndCompanyId(orderId, companyId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));
    }

    public DailyOrderList getOrderListById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        return dailyOrderListRepository.findByIdAndCompanyId(orderListId, companyId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public Page<DailyOrderList> getAll(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        User user = getUser(currentUser);

        return dailyOrderListRepository.findAllByCompanyId(user.getCompany().getId(), pageable);
    }

    public boolean checkDailyOrderListExists(@CurrentUser UserPrincipal currentUser) {
        User user = getUser(currentUser);

        Long companyId = user.getCompany().getId();

        return dailyOrderListRepository.existsByIsOpenTrueAndCompanyId(companyId);
    }

    public boolean openOrderList(@CurrentUser UserPrincipal currentUser) {
        User user = getUser(currentUser);

        DailyOrderList orderList = new DailyOrderList.DailyOrderListBuilder()
                .setDailyIncome(0.00)
                .setNumberOfOrders(0)
                .setIsOpen(Boolean.TRUE)
                .setOrders(new LinkedHashSet<>())
                .setCompany(user.getCompany())
                .build();

        dailyOrderListRepository.save(orderList);

        return true;
    }

    public DailyOrderList getOpenedOrderList(@CurrentUser UserPrincipal currentUser) {
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        return dailyOrderListRepository.findDailyOrderListByIsOpenTrueAndCompanyId(companyId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));
    }

    public DailyOrderList addOrderToList(@CurrentUser UserPrincipal currentUser, Long orderId) {
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        Order order = getOrderByIdAndCompanyId(orderId, companyId);

        DailyOrderList dailyOrderList = getOpenedOrderList(currentUser);

        Optional<Order> result = getOptionalOrder(dailyOrderList, orderId);

        if (result.isPresent()) {
            throw new OrderListExistsException(OrderMessages.ORDER_EXISTS_ON_LIST.getMessage());
        }

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.add(order);

        double income = order.getCartOrdered().getTotalPrice() + dailyOrderList.getDailyIncome();
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
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        Order order = getOrderByIdAndCompanyId(orderId, companyId);

        DailyOrderList dailyOrderList = getOpenedOrderList(currentUser);

        Optional<Order> result = getOptionalOrder(dailyOrderList, orderId);

        if (!result.isPresent()) {
            throw new OrderListExistsException(OrderMessages.ORDER_NO_EXISTS_ON_LIST.getMessage());
        }

        Set<Order> orders = new LinkedHashSet<>(dailyOrderList.getOrders());
        orders.remove(order);

        double income = dailyOrderList.getDailyIncome() - order.getCartOrdered().getTotalPrice();
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
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        DailyOrderList dailyOrderList = dailyOrderListRepository.findDailyOrderListByIsOpenTrueAndCompanyId(companyId)
                .orElseThrow(() -> new OrderListNotFoundException(OrderMessages.ORDER_LIST_NOT_OPEN.getMessage()));

        dailyOrderList.setIsOpen(Boolean.FALSE);

        dailyOrderListRepository.save(dailyOrderList);

        return dailyOrderList;
    }

    public ApiResponse deleteById(@CurrentUser UserPrincipal currentUser, Long orderListId) {
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        DailyOrderList orderList = dailyOrderListRepository.findByIdAndCompanyId(orderListId, companyId)
                .orElseThrow(() -> new OrderListExistsException(OrderMessages.ORDER_LIST_NOT_FOUND.getMessage()));

        dailyOrderListRepository.delete(orderList);

        return new ApiResponse(true, OrderMessages.ORDER_LIST_DELETED.getMessage());
    }

    public StatisticsReportResponse countDailyOrders(@CurrentUser UserPrincipal currentUser) {
        User user = getUser(currentUser);
        Long companyId = user.getCompany().getId();

        DailyOrderList dailyOrderList = dailyOrderListRepository.findByIsOpenIsTrueAndCompanyId(companyId);

        Integer productsCount = dailyOrderList.getOrders().stream()
                .mapToInt(v -> v.getCartOrdered()
                        .getLineItemsOrdered()
                        .size())
                .sum();

        return new StatisticsReportResponse(
                dailyOrderList.getNumberOfOrders(),
                dailyOrderList.getDailyIncome(),
                productsCount
        );
    }

}
