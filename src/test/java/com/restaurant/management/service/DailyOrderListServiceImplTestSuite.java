package com.restaurant.management.service;

import com.restaurant.management.domain.Cart;
import com.restaurant.management.domain.DailyOrderList;
import com.restaurant.management.domain.Order;
import com.restaurant.management.repository.DailyOrderListRepository;
import com.restaurant.management.repository.OrderRepository;
import com.restaurant.management.service.impl.DailyOrderListServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DailyOrderListServiceImplTestSuite {
    @InjectMocks
    private DailyOrderListServiceImpl dailyOrderListServiceImpl;
    @Mock
    private DailyOrderListRepository dailyOrderListRepository;
    @Mock
    private OrderRepository orderRepository;

    private static final String ORDER_NUMBER = "orderNumber";
    private static final String ORDER_LIST_UNIQUE_ID = "KDI2J2N4EM";

    @Test
    public void shouldGetOrderListByUniqueId() {
        //GIVEN
        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(532.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(new LinkedHashSet<>());

        when(dailyOrderListRepository.findByUniqueId(ORDER_LIST_UNIQUE_ID)).thenReturn(Optional.of(dailyOrderList));
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.getOrderListById(ORDER_LIST_UNIQUE_ID);
        //THEN
        assertAll(
                () -> assertEquals(result.getUniqueId(), ORDER_LIST_UNIQUE_ID),
                () -> assertTrue(result.isOpen()),
                () -> assertEquals(result.getDailyIncome(), dailyOrderList.getDailyIncome())
        );
    }

//    @Test
//    public void shouldGetAllDailyOrderLists() {
//        //GIVEN
//        DailyOrderList list1 = new DailyOrderList();
//        list1.setUniqueId(ORDER_LIST_UNIQUE_ID);
//        list1.setDailyIncome(532.00);
//        list1.setIsOpen(Boolean.TRUE);
//        list1.setOrders(new LinkedHashSet<>());
//
//        DailyOrderList list2 = new DailyOrderList();
//        list2.setUniqueId(ORDER_LIST_UNIQUE_ID + 1);
//        list2.setDailyIncome(68.00);
//        list2.setIsOpen(Boolean.TRUE);
//        list2.setOrders(new LinkedHashSet<>());
//
//        List<DailyOrderList> dailyOrdersLists = new ArrayList<>();
//        dailyOrdersLists.add(list1);
//        dailyOrdersLists.add(list2);
//
//        when(dailyOrderListRepository.findAll()).thenReturn(dailyOrdersLists);
//        //WHEN
//        List<DailyOrderList> result = dailyOrderListService.getAll();
//        //THEN
//        assertAll(
//                () -> assertEquals(result.get(0).getUniqueId(), ORDER_LIST_UNIQUE_ID),
//                () -> assertEquals(result.get(1).getUniqueId(), ORDER_LIST_UNIQUE_ID +1),
//                () -> assertEquals(result.size(), 2),
//                () -> assertEquals(result.get(0).getDailyIncome().doubleValue(), 532.00),
//                () -> assertEquals(result.get(1).getDailyIncome().doubleValue(), 68.00)
//        );
//    }

    @Test
    public void shouldOpenDailyOrderList() {
        //GIVEN
        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(0.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(new LinkedHashSet<>());

        when(dailyOrderListRepository.existsByIsOpenTrue()).thenReturn(Boolean.FALSE);
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.openOrderList();
        //THEN
        assertAll(
                () -> assertTrue(result.isOpen()),
                () -> assertEquals(result.getDailyIncome().doubleValue(), 0.00),
                () -> assertEquals(result.getUniqueId().length(), 10)
        );
    }

    @Test
    public void shouldGetOpenedOrderList() {
        //GIVEN
        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(124.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(new LinkedHashSet<>());

        when(dailyOrderListRepository.findDailyOrderListByIsOpenTrue()).thenReturn(Optional.of(dailyOrderList));
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.getOpenedOrderList();
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getDailyIncome().doubleValue(), 124.00),
                () -> assertEquals(result.getUniqueId(), ORDER_LIST_UNIQUE_ID)
        );
    }

    @Test
    public void shouldAddOrderToDailyOrderList() {
        //GIVEN
        Order order = new Order(
                ORDER_NUMBER,
                Calendar.getInstance(),
                "ORDERED",
                300.00,
                new Cart()
        );

        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(0.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(Collections.singleton(order));

        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.of(order));
        when(dailyOrderListRepository.findDailyOrderListByIsOpenTrue()).thenReturn(Optional.of(dailyOrderList));
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.addOrderToList(ORDER_LIST_UNIQUE_ID);
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getDailyIncome().doubleValue(), 300.00),
                () -> assertEquals(result.getOrders().size(), 1),
                () -> assertTrue(result.isOpen())
        );
    }

    @Test
    public void shouldRemoveOrderFromDailyOrderList() {
        //GIVEN
        Order order = new Order(
                ORDER_NUMBER,
                Calendar.getInstance(),
                "ORDERED",
                300.00,
                new Cart()
        );

        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(300.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(Collections.singleton(order));

        when(orderRepository.findByOrderNumber(anyString())).thenReturn(Optional.of(order));
        when(dailyOrderListRepository.findDailyOrderListByIsOpenTrue()).thenReturn(Optional.of(dailyOrderList));
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.removeOrderFromList(ORDER_NUMBER);
        //THEN
        assertAll(
                () -> assertEquals(result.getOrders().size(), 0),
                () -> assertEquals(result.getDailyIncome().doubleValue(), 0.00),
                () -> assertTrue(result.isOpen()),
                () -> assertEquals(result.getUniqueId(), ORDER_LIST_UNIQUE_ID)
        );
    }

    @Test
    public void shouldCloseDailyOrderList() {
        //GIVEN
        Order order = new Order(
                ORDER_NUMBER,
                Calendar.getInstance(),
                "ORDERED",
                300.00,
                new Cart()
        );

        DailyOrderList dailyOrderList = new DailyOrderList();

        dailyOrderList.setUniqueId(ORDER_LIST_UNIQUE_ID);
        dailyOrderList.setDailyIncome(300.00);
        dailyOrderList.setIsOpen(Boolean.TRUE);
        dailyOrderList.setOrders(Collections.singleton(order));

        when(dailyOrderListRepository.findDailyOrderListByIsOpenTrue()).thenReturn(Optional.of(dailyOrderList));
        //WHEN
        DailyOrderList result = dailyOrderListServiceImpl.closeDailyList();
        //THEN
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.getUniqueId(), ORDER_LIST_UNIQUE_ID),
                () -> assertFalse(result.isOpen()),
                () -> assertEquals(result.getDailyIncome().doubleValue(), 300.00)
        );
    }
}