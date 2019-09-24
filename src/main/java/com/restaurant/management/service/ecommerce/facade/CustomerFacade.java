package com.restaurant.management.service.ecommerce.facade;

import com.restaurant.management.domain.ecommerce.Customer;
import com.restaurant.management.domain.ecommerce.dto.CustomerDTO;
import com.restaurant.management.domain.ecommerce.dto.CustomerFormDTO;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.ecommerce.CustomerService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public final class CustomerFacade {

    private CustomerMapper customerMapper;
    private CustomerService customerService;

    @Autowired
    public CustomerFacade(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    public CustomerFormDTO registerCustomer(@CurrentUser UserPrincipal currentUser, CustomerFormDTO request) {
        Customer customer = customerService.registerCustomer(currentUser, request);

        return customerMapper.mapToCustomerFormDTO(customer);
    }

    public CustomerFormDTO updateCustomer(@CurrentUser UserPrincipal currentUser, CustomerFormDTO request) {
        Customer customer = customerService.updateCustomer(currentUser, request);

        return customerMapper.mapToCustomerFormDTO(customer);
    }

    public Page<CustomerFormDTO> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Page<Customer> customers = customerService.getAllCustomers(currentUser, pageable);

        return customerMapper.mapToCustomerFormDTOPage(customers);
    }

    public ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        return customerService.deleteCustomerById(currentUser, id);
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] customerIds) {
        return customerService.deleteAllByIds(currentUser,customerIds);
    }

    public CustomerDTO getCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        Customer customer = customerService.getCustomerById(currentUser, id);

        return customerMapper.mapToCustomerDto(customer);
    }

    public Page<CustomerDTO> getAllCustomersWithNameWithin(@CurrentUser UserPrincipal currentUser,
                                                           String name, Pageable pageable) {
        Page<Customer> customers = customerService.getAllByNameWithin(currentUser, name, pageable);

        return customerMapper.mapToCustomerDtoPage(customers);
    }

    public Page<CustomerDTO> getAllCustomersWithPhoneWithin(@CurrentUser UserPrincipal currentUser,
                                                            String phone, Pageable pageable) {
        Page<Customer> customers = customerService.getAllByPhoneWithin(currentUser, phone, pageable);

        return customerMapper.mapToCustomerDtoPage(customers);
    }

    public Page<CustomerDTO> getAllCustomersByLastNameWithin(@CurrentUser UserPrincipal currentUser,
                                                             String lastName,
                                                             Pageable pageable) {

        Page<Customer> customers = customerService.getAllByLastNameWithin(currentUser, lastName, pageable);

        return customerMapper.mapToCustomerDtoPage(customers);
    }
}
