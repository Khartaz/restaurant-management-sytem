package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.request.customer.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer registerCustomer(@CurrentUser UserPrincipal currentUser, SignUpCustomerRequest request);

    Page<Customer> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long id);

    Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long id);

    Page<Customer> getAllByNameWithin(@CurrentUser UserPrincipal currentUser, String name, Pageable pageable);

    Page<Customer> getAllByPhoneNumberWithin(@CurrentUser UserPrincipal currentUser, String phoneNumber, Pageable pageable);

    Page<Customer> getAllByLastnameWithin(@CurrentUser UserPrincipal currentUser, String lastname, Pageable pageable);
}
