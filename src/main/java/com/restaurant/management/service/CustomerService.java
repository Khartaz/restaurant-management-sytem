package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.utils.Utils;
import com.restaurant.management.web.request.SingUpCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private Utils utils;

    @Autowired
    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //@RolesAllowed({"MANAGER", "ROLE_ADMIN"})
    public Customer createCustomer(SingUpCustomerRequest request) {
        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setLastname(request.getLastname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);

        return customer;
    }
}
