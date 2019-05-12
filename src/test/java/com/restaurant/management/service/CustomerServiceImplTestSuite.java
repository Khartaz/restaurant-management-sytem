package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTestSuite {
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private CustomerRepository customerRepository;

    private static final String CUSTOMER_NAME = "Customer name";
    private static final String CUSTOMER_LASTNAME = "Customer lastname";
    private static final String CUSTOMER_EMAIL = "Customer email";
    private static final long PHONE_NUMBER = 9289310L;

    @Test
    public void shouldCreateCustomer() {
        //GIVEN
        SignUpCustomerRequest request = new SignUpCustomerRequest
                (CUSTOMER_NAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, PHONE_NUMBER);

        when(customerRepository.existsByPhoneNumber(PHONE_NUMBER)).thenReturn(Boolean.FALSE);
        when(customerRepository.existsByEmail(anyString())).thenReturn(Boolean.FALSE);
        //WHEN
        Customer result = customerService.createCustomer(request);
        //THEN
        assertAll(
                () -> assertEquals(result.getPhoneNumber(), request.getPhoneNumber()),
                () -> assertEquals(result.getLastname(), request.getLastname()),
                () -> assertEquals(result.getName(), request.getName()),
                () -> assertEquals(result.getEmail(), request.getEmail())
        );
    }

    @Test
    public void shouldGetAllCustomers() {
        //GIVEN
        Customer customer1 = new Customer(CUSTOMER_NAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, PHONE_NUMBER);
        Customer customer2 = new Customer(CUSTOMER_NAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, PHONE_NUMBER);
        Customer customer3 = new Customer(CUSTOMER_NAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, PHONE_NUMBER);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        Pageable pageable = PageRequest.of(0, 1);

        when(customerRepository.findAll(pageable)).thenReturn(new PageImpl<>(customers));
        //WHEN
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);
        List<Customer> result = customerPage.get().collect(Collectors.toList());
        //THEN
        assertAll(
                () -> assertEquals(result.size(), customers.size()),
                () -> assertEquals(result.get(0).getEmail(), customers.get(0).getEmail()),
                () -> assertEquals(result.get(0).getPhoneNumber(), customers.get(0).getPhoneNumber())
        );
    }

    @Test
    public void shouldGetCustomerById() {
        //GIVEN
        Optional<Customer> optionalCustomer = Optional.of(
                new Customer(1L, CUSTOMER_NAME, CUSTOMER_LASTNAME, CUSTOMER_EMAIL, PHONE_NUMBER
                ));

        when(customerRepository.findById(1L)).thenReturn(optionalCustomer);
        //WHEN
        Customer result = customerService.getCustomerById(1L);
        //THEN
        Customer customer = optionalCustomer.get();
        assertAll(
                () -> assertEquals(result.getId(), customer.getId()),
                () -> assertEquals(result.getPhoneNumber(), customer.getPhoneNumber()),
                () -> assertEquals(result.getName(), customer.getName()),
                () -> assertEquals(result.getLastname(), customer.getLastname()),
                () -> assertEquals(result.getEmail(), customer.getEmail())
        );
    }

}