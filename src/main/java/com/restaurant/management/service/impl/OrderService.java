package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.order.OrderMessages;
import com.restaurant.management.exception.order.OrderNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.*;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private OrderRepository orderRepository;
    private CartService cartService;
    private CustomerRepository customerRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        CustomerRepository customerRepository,
                        AccountUserRepository accountUserRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.customerRepository = customerRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public Long countRestaurantOrders(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return orderRepository.countAllByRestaurantInfoId(restaurantId);
    }

    public Order processOrder(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Cart cart = cartService.confirmCart(currentUser, customerId);

        Long orderNumber = countRestaurantOrders(currentUser) + 1;

        Order order = new Order.OrderBuilder()
                .setOrdered(Calendar.getInstance())
                .setStatus(OrderStatus.ORDERED)
                .setOrderNumber(orderNumber)
                .setTotalPrice(new Order().calculateTotalPrice(cart))
                .setCart(cart)
                .setRestaurantInfo(cart.getRestaurantInfo())
                .build();

        orderRepository.save(order);

        return order;
    }

    public Page<Order> getAllOrders(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return orderRepository.findAllByRestaurantInfoId(restaurantId, pageable);
    }

    public Order getByOrderId(@CurrentUser UserPrincipal currentUser, Long orderId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        return orderRepository.findByIdAndRestaurantInfoId(orderId, restaurantId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage() + orderId));
    }

    public ApiResponse deleteOrder(@CurrentUser UserPrincipal currentUser, Long orderId) {
        Order order = getByOrderId(currentUser, orderId);

        orderRepository.deleteById(order.getId());

        return new ApiResponse(true, OrderMessages.ORDER_DELETED.getMessage());
    }

    public Page<Order> getCustomerOrdersById(@CurrentUser UserPrincipal currentUser, Long customerId, Pageable pageable) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        Customer customer = customerRepository.findByIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        Page<Order> orderList = orderRepository.findAllByRestaurantInfoId(restaurantId, pageable);

        List<Order> customerOrders = orderList.stream()
                .filter(v -> v.getCart().getCustomer().getPhoneNumber().equals(customer.getPhoneNumber()))
                .collect(Collectors.toList());

        return new PageImpl<>(customerOrders);
    }

    public Order getOrderByCustomerIdAndOrderId(@CurrentUser UserPrincipal currentUser,
                                                Long customerId, Long orderId) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = accountUser.getRestaurantInfo().getId();

        Customer customer = customerRepository.findByIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        return orderRepository.findByIdAndRestaurantInfoId(orderId, restaurantId)
                .filter(v -> v.getCart().getCustomer().getPhoneNumber().equals(customer.getPhoneNumber()))
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));
    }
}
