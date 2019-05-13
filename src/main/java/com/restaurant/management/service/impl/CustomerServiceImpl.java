package com.restaurant.management.service.impl;

import com.restaurant.management.domain.AccountUser;
import com.restaurant.management.domain.Customer;
import com.restaurant.management.domain.RestaurantInfo;
import com.restaurant.management.domain.SessionCart;
import com.restaurant.management.exception.customer.CustomerExistsException;
import com.restaurant.management.exception.customer.CustomerMessages;
import com.restaurant.management.exception.customer.CustomerNotFoundException;
import com.restaurant.management.exception.user.UserMessages;
import com.restaurant.management.exception.user.UserNotFoundException;
import com.restaurant.management.repository.AccountUserRepository;
import com.restaurant.management.repository.SessionCartRepository;
import com.restaurant.management.repository.CustomerRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;
import com.restaurant.management.service.AccountUserService;
import com.restaurant.management.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private SessionCartRepository sessionCartRepository;
    private AccountUserRepository accountUserRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               SessionCartRepository sessionCartRepository,
                               AccountUserRepository accountUserRepository) {
        this.customerRepository = customerRepository;
        this.sessionCartRepository = sessionCartRepository;
        this.accountUserRepository = accountUserRepository;
    }

    public Customer createCustomer(@CurrentUser UserPrincipal currentUser,
                                   SignUpCustomerRequest request) {

        validateEmailAndPhoneNumber(request.getPhoneNumber(), request.getEmail());
        
        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setLastname(request.getLastname());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setRestaurantInfo(getRestaurantInfo(currentUser));

        customerRepository.save(customer);

        return customer;
    }

    private void validateEmailAndPhoneNumber(Long phoneNumber, String email) {
        if (customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getMessage());
        }
        if (customerRepository.existsByEmail(email)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getMessage());
        }
    }

    private RestaurantInfo getRestaurantInfo(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return accountUser.getRestaurantInfo();
    }

    public Page<Customer> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findAllByRestaurantInfoId(pageable, restaurantId);
    }

    public ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        Customer customer = customerRepository.findByIdAndRestaurantInfoId(customerId, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + customerId));

        Optional<SessionCart> sessionCart = sessionCartRepository.findByCustomer(customer);

        sessionCart.ifPresent(v -> sessionCartRepository.delete(v));

        customerRepository.deleteById(customer.getId());

        return new ApiResponse(true, CustomerMessages.CUSTOMER_DELETED.getMessage());
    }

    public Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findByIdAndRestaurantInfoId(id, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id));
    }
}
