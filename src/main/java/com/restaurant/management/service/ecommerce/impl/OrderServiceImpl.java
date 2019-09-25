package com.restaurant.management.service.ecommerce.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.exception.ecommerce.customer.CustomerMessages;
import com.restaurant.management.exception.ecommerce.customer.CustomerNotFoundException;
import com.restaurant.management.exception.ecommerce.order.OrderMessages;
import com.restaurant.management.exception.ecommerce.order.OrderNotFoundException;
import com.restaurant.management.exception.ecommerce.user.UserMessages;
import com.restaurant.management.exception.ecommerce.user.UserNotFoundException;
import com.restaurant.management.repository.ecommerce.UserRepository;
import com.restaurant.management.repository.ecommerce.CustomerRepository;
import com.restaurant.management.repository.ecommerce.OrderRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.CartOrderedService;
import com.restaurant.management.service.ecommerce.OrderService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private CartOrderedService cartOrderedService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            UserRepository userRepository,
                            CartOrderedService cartOrderedService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.cartOrderedService = cartOrderedService;
    }

    public Long countCompanyOrders(@CurrentUser UserPrincipal currentUser) {
        User user = getUser(currentUser);

        Long restaurantId = user.getCompany().getId();

        return orderRepository.countAllByCompanyId(restaurantId);
    }

    public Order processOrder(@CurrentUser UserPrincipal currentUser, Long customerId) {
        CartOrdered cartOrdered = cartOrderedService.processCartToCartOrdered(currentUser, customerId);

        String orderNumber = String.valueOf(countCompanyOrders(currentUser) + 1);

        Order order = new Order.OrderBuilder()
                .setStatus(OrderStatus.ORDERED)
                .setOrderNumber(Order.createOrderNumber(orderNumber))
                .setAssignedTo(currentUser.getId())
                .setOrderType(OrderType.DELIVERY)
                .setCartOrdered(cartOrdered)
                .setCompany(cartOrdered.getCompany())
                .build();

        orderRepository.save(order);

        return order;
    }

    public Page<Order> getAllOrders(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        User user = getUser(currentUser);

        Company company = user.getCompany();

        return orderRepository.findAllByCompany(company, pageable);
    }

    public Order getByOrderId(@CurrentUser UserPrincipal currentUser, Long orderId) {
        User user = getUser(currentUser);

        Long restaurantId = user.getCompany().getId();

        return orderRepository.findByIdAndCompanyId(orderId, restaurantId)
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage() + orderId));
    }

    public ApiResponse deleteOrder(@CurrentUser UserPrincipal currentUser, Long orderId) {
        Order order = getByOrderId(currentUser, orderId);

        orderRepository.deleteById(order.getId());

        return new ApiResponse(true, OrderMessages.ORDER_DELETED.getMessage());
    }

    public Page<Order> getCustomerOrdersById(@CurrentUser UserPrincipal currentUser, Long customerId, Pageable pageable) {
        User user = userRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Company company= user.getCompany();

        Customer customer = customerRepository.findByIdAndCompanyIdAndIsDeletedIsFalse(customerId, company.getId())
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        Page<Order> orderList = orderRepository.findAllByCompany(company, pageable);

        List<Order> customerOrders = orderList.stream()
                .filter(v -> v.getCartOrdered().getCustomerOrdered().getPhone().equals(customer.getPhone()))
                .collect(Collectors.toList());

        return new PageImpl<>(customerOrders);
    }

    public Order getOrderByCustomerIdAndOrderId(@CurrentUser UserPrincipal currentUser,
                                                Long customerId, Long orderId) {
        User user = userRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        Long restaurantId = user.getCompany().getId();

        Customer customer = customerRepository.findByIdAndCompanyIdAndIsDeletedIsFalse(customerId, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage()));

        return orderRepository.findByIdAndCompanyId(orderId, restaurantId)
                .filter(v -> v.getCartOrdered().getCustomerOrdered().getPhone().equals(customer.getPhone()))
                .orElseThrow(() -> new OrderNotFoundException(OrderMessages.ORDER_ID_NOT_FOUND.getMessage()));
    }

    public Page<Order> getAllOfCurrentYear(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        User user = getUser(currentUser);
        Long restaurantId = user.getCompany().getId();

        Calendar currentYear = new GregorianCalendar();
        int YEAR = currentYear.get(Calendar.YEAR);

        Calendar startDate = new GregorianCalendar(YEAR,Calendar.JANUARY,1,0,0,1);
        Calendar endDate = new GregorianCalendar(YEAR,Calendar.DECEMBER,31,23,59,59);

        return orderRepository.findByCompanyIdAndCreatedAtBetween(restaurantId, startDate.getTimeInMillis(), endDate.getTimeInMillis(), pageable);
    }

    private User getUser(@CurrentUser UserPrincipal currentUser) {
        return userRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));
    }
}
