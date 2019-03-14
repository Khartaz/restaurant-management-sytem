package com.restaurant.management.service;

import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.domain.archive.CustomerArchive;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.repository.archive.CustomerArchiveRepository;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.web.request.SingUpCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private SessionCartRepository sessionCartRepository;
    private CustomerArchiveRepository customerArchiveRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper,
                           SessionCartRepository sessionCartRepository,
                           CustomerArchiveRepository customerArchiveRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.sessionCartRepository = sessionCartRepository;
        this.customerArchiveRepository = customerArchiveRepository;
    }

    //@RolesAllowed({"MANAGER", "ROLE_ADMIN"})
    public CustomerDto createCustomer(SingUpCustomerRequest request) {

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

        CustomerArchive customerArchive = customerMapper.mapToCustomerArchive(customer);
        customerArchiveRepository.save(customerArchive);

        return customerMapper.mapToCustomerDto(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customerMapper.mapToCustomerDtoList(customers);
    }

    public void deleteCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            SessionCart sessionCart = sessionCartRepository.findByCustomer(customer.get());

            sessionCartRepository.delete(sessionCart);

            customerRepository.deleteById(customer.get().getId());
        } else {
            throw new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id);
        }
    }

    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        if (!customer.isPresent()) {
            throw new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id);
        }

        return customerMapper.mapToCustomerDto(customer.get());
    }
}
