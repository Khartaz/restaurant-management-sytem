package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.web.request.SingUpCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    //@RolesAllowed({"MANAGER", "ROLE_ADMIN"})
    public CustomerDto createCustomer(SingUpCustomerRequest request) {

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getErrorMessage());
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getErrorMessage());
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setLastname(request.getLastname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);

        return customerMapper.mapToCustomerDto(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customerMapper.mapToCustomerDtoList(customers);
    }
}
