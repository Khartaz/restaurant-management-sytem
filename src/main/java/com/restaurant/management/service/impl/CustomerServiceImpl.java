package com.restaurant.management.service.impl;

import com.restaurant.management.domain.*;
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
import com.restaurant.management.service.CustomerService;
import com.restaurant.management.web.request.customer.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
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

    public Customer registerCustomer(@CurrentUser UserPrincipal currentUser,
                                     SignUpCustomerRequest request) {

        validateEmailAndPhoneNumber(currentUser, request.getPhoneNumber(), request.getEmail());

        CustomerAddress address = new CustomerAddress.CustomerAddressBuilder()
                .setStreetAndNumber(request.getCustomerAddressRequest().getStreetAndNumber())
                .setPostCode(request.getCustomerAddressRequest().getPostCode())
                .setCity(request.getCustomerAddressRequest().getCity())
                .setCountry(request.getCustomerAddressRequest().getCountry())
                .build();

        Customer customer = new Customer.CustomerBuilder()
                .setName(request.getName())
                .setLastname(request.getLastname())
                .setEmail(request.getEmail())
                .setPhoneNumber(request.getPhoneNumber())
                .setRestaurantInfo(getRestaurantInfo(currentUser))
                .setCustomerAddress(address)
                .build();

        customerRepository.save(customer);

        return customer;
    }

    private void validateEmailAndPhoneNumber(@CurrentUser UserPrincipal currentUser, Long phoneNumber, String email) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        if (customerRepository.existsByPhoneNumberAndRestaurantInfoId(phoneNumber, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getMessage());
        }
        if (customerRepository.existsByEmailAndRestaurantInfoId(email, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getMessage());
        }
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

        sessionCart.ifPresent(s -> sessionCartRepository.delete(s));

        customerRepository.deleteById(customer.getId());

        return new ApiResponse(true, CustomerMessages.CUSTOMER_DELETED.getMessage());
    }

    public Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findByIdAndRestaurantInfoId(id, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id));
    }

    private RestaurantInfo getRestaurantInfo(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return accountUser.getRestaurantInfo();
    }
}
