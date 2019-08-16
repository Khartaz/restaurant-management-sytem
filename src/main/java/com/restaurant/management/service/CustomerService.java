package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.Customer;
import com.restaurant.management.domain.ecommerce.dto.CustomerDTO;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    Customer registerCustomer(@CurrentUser UserPrincipal currentUser, CustomerDTO request);

    Customer updateCustomer(@CurrentUser UserPrincipal currentUser, CustomerDTO request);

    Page<Customer> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable);

    ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long id);

    ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] customerIds);

    Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long id);

    Page<Customer> getAllByNameWithin(@CurrentUser UserPrincipal currentUser, String name, Pageable pageable);

    Page<Customer> getAllByPhoneWithin(@CurrentUser UserPrincipal currentUser, String phone, Pageable pageable);

    Page<Customer> getAllByLastNameWithin(@CurrentUser UserPrincipal currentUser, String lastName, Pageable pageable);
}
