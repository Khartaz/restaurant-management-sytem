package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
import com.restaurant.management.domain.ecommerce.dto.CustomerFormDTO;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.CartRepository;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository,
                               AccountUserRepository accountUserRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public Customer registerCustomer(@CurrentUser UserPrincipal currentUser, CustomerFormDTO request) {
        validateEmailAndPhoneNumber(currentUser, request.getPhone(), request.getEmail());

        CustomerAddress address = new CustomerAddress();
        Stream.of(address)
                .forEach(a -> {
                    a.setStreetAndNumber(request.getStreetAndNumber());
                    a.setPostCode(request.getPostCode());
                    a.setCity(request.getCity());
                    a.setCountry(request.getCountry());
                });

        Customer customer = new Customer();
        Stream.of(customer)
                .forEach(c -> {
                   c.setName(request.getName());
                   c.setLastName(request.getLastName());
                   c.setEmail(request.getEmail());
                   c.setPhone(request.getPhone());
                   c.setCompany(getCompany(currentUser));
                   c.setCustomerAddress(address);
                   c.setDeleted(Boolean.FALSE);

                    customerRepository.save(c);
                });

        return customer;
    }

    public Customer updateCustomer(@CurrentUser UserPrincipal currentUser, CustomerFormDTO request) {
        validateEmailAndPhoneNumber(currentUser, request.getPhone(), request.getEmail());

        Customer customer = getCustomerById(currentUser, request.getId());

        Stream.of(customer)
                .forEach(c -> {
                    c.setName(request.getName());
                    c.setLastName(request.getLastName());
                    c.setEmail(request.getEmail());
                    c.setPhone(request.getPhone());
                    c.getCustomerAddress().setCountry(request.getCountry());
                    c.getCustomerAddress().setCity(request.getCity());
                    c.getCustomerAddress().setPostCode(request.getPostCode());
                    c.getCustomerAddress().setStreetAndNumber(request.getStreetAndNumber());

                    customerRepository.save(c);
                });

        return customer;
    }
    private void validateEmailAndPhoneNumber(@CurrentUser UserPrincipal currentUser, String phone, String email) {
        Long companyId = getCompany(currentUser).getId();

        if (customerRepository.existsByPhoneAndCompanyIdAndIsDeletedIsFalse(phone, companyId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getMessage());
        }
        if (customerRepository.existsByEmailAndCompanyIdAndIsDeletedIsFalse(email, companyId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getMessage());
        }
    }

    public Page<Customer> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {

        Company company = getCompany(currentUser);

        return customerRepository.findAllByCompanyAndIsDeletedIsFalse(pageable, company);
    }

    public ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Customer customer = getCustomerById(currentUser, customerId);

        List<Cart> carts = cartRepository.findAllByCustomer(customer);

        carts.iterator().forEachRemaining(cart -> {
            cart.setDeleted(Boolean.TRUE);
            cartRepository.save(cart);
        });

        customer.setDeleted(Boolean.TRUE);
        customerRepository.save(customer);

        return new ApiResponse(true, CustomerMessages.CUSTOMER_DELETED.getMessage());
    }

    public ApiResponse deleteAllByIds(@CurrentUser UserPrincipal currentUser, Long[] customerIds) {
        List<Long> ids = new ArrayList<>(Arrays.asList(customerIds));

        List<Customer> customers = customerRepository.findAllByIdIn(ids);
        Stream.of(customers)
                .forEach(c -> {
                    c.iterator().forEachRemaining(v -> v.setDeleted(Boolean.TRUE));
                    c.iterator().forEachRemaining(v -> customerRepository.save(v));
                });

        return new ApiResponse(true, CustomerMessages.CUSTOMERS_DELETED.getMessage());
    }

    public Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Long companyId = getCompany(currentUser).getId();

        return customerRepository.findByIdAndCompanyId(customerId, companyId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + customerId));
    }

    private Company getCompany(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findByIdAndIsDeletedIsFalse(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return accountUser.getCompany();
    }

    public Page<Customer> getAllByNameWithin(@CurrentUser UserPrincipal currentUser, String name, Pageable pageable) {
        Long restaurantId = getCompany(currentUser).getId();

        return customerRepository.findAllByNameIsContainingAndCompanyId(name, restaurantId, pageable);
    }

    public Page<Customer> getAllByPhoneWithin(@CurrentUser UserPrincipal currentUser, String phone, Pageable pageable) {
        Long restaurantId = getCompany(currentUser).getId();

        return customerRepository.findAllByPhoneIsContainingAndCompanyId(phone, restaurantId, pageable);
    }

    public Page<Customer> getAllByLastNameWithin(@CurrentUser UserPrincipal currentUser, String lastName, Pageable pageable) {
        Long restaurantId = getCompany(currentUser).getId();

        return customerRepository.findAllByLastNameContainingAndCompanyId(lastName, restaurantId, pageable);
    }
}
