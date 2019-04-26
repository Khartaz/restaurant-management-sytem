package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.web.request.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private SessionCartRepository sessionCartRepository;


    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           SessionCartRepository sessionCartRepository) {
        this.customerRepository = customerRepository;
        this.sessionCartRepository = sessionCartRepository;
    }

    //@RolesAllowed({"ROLE_MANAGER", "ROLE_ADMIN"})
    public Customer createCustomer(SignUpCustomerRequest request) {
        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getMessage());
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getMessage());
        }

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setLastname(request.getLastname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);

        return customer;
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public ApiResponse deleteCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            Optional<SessionCart> sessionCart = sessionCartRepository.findByCustomer(customer.get());

            sessionCart.ifPresent(v -> sessionCartRepository.delete(v));

            customerRepository.deleteById(customer.get().getId());

            return new ApiResponse(true, CustomerMessages.CUSTOMER_DELETED.getMessage());

        } else {
            throw new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id);
        }
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id);
        }

        return customer.get();
    }
}
