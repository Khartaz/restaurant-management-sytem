package com.restaurant.management.service.impl;

import com.restaurant.management.domain.ecommerce.*;
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
import com.restaurant.management.web.request.customer.SignUpCustomerRequest;
import com.restaurant.management.web.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.restaurant.management.utils.Validation.validatePhoneNumber;

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

    private void validateEmailAndPhoneNumber(@CurrentUser UserPrincipal currentUser, String phoneNumber, String email) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        validatePhoneNumber(phoneNumber);

        if (customerRepository.existsByPhoneNumberAndCompanyId(phoneNumber, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_PHONE_EXISTS.getMessage());
        }
        if (customerRepository.existsByEmailAndCompanyId(email, restaurantId)) {
            throw new CustomerExistsException(CustomerMessages.CUSTOMER_EMAIL_EXISTS.getMessage());
        }
    }

    public Page<Customer> getAllCustomers(@CurrentUser UserPrincipal currentUser, Pageable pageable) {

        Long companyId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findAllByCompanyIdAndIsDeletedIsFalse(pageable, companyId);
    }

    public ApiResponse deleteCustomerById(@CurrentUser UserPrincipal currentUser, Long customerId) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        Customer customer = customerRepository.findByIdAndCompanyId(customerId, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + customerId));

        Optional<Cart> sessionCart = cartRepository.findByCustomer(customer);

        sessionCart.ifPresent(s -> cartRepository.delete(s));

        customerRepository.deleteById(customer.getId());

        return new ApiResponse(true, CustomerMessages.CUSTOMER_DELETED.getMessage());
    }

    public Customer getCustomerById(@CurrentUser UserPrincipal currentUser, Long id) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findByIdAndCompanyId(id, restaurantId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerMessages.ID_NOT_FOUND.getMessage() + id));
    }

    private Company getRestaurantInfo(@CurrentUser UserPrincipal currentUser) {
        AccountUser accountUser = accountUserRepository.findById(currentUser.getId())
                .orElseThrow(() -> new UserNotFoundException(UserMessages.ID_NOT_FOUND.getMessage()));

        return accountUser.getCompany();
    }

    public Page<Customer> getAllByNameWithin(@CurrentUser UserPrincipal currentUser, String name, Pageable pageable) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findAllByNameIsContainingAndCompanyId(name, restaurantId, pageable);
    }

    public Page<Customer> getAllByPhoneNumberWithin(@CurrentUser UserPrincipal currentUser, String phoneNumber, Pageable pageable) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findAllByPhoneNumberIsContainingAndCompanyId(phoneNumber, restaurantId, pageable);
    }

    public Page<Customer> getAllByLastnameWithin(@CurrentUser UserPrincipal currentUser, String lastname, Pageable pageable) {
        Long restaurantId = getRestaurantInfo(currentUser).getId();

        return customerRepository.findAllByLastnameContainingAndCompanyId(lastname, restaurantId, pageable);
    }
}
