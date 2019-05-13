package com.restaurant.management.web.controller;

import com.google.gson.Gson;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.facade.SessionCartFacade;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.web.response.CartResponse;
import com.restaurant.management.web.response.CustomerResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class, secure = false)
public class CustomerControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CustomerFacade customerFacade;
    @MockBean
    private CustomerMapper customerMapper;
    @MockBean
    private SessionCartFacade sessionCartFacade;
    @MockBean
    private CartMapper cartMapper;

    private static final String CART_UNIQUE_ID = "uniqueId";

    private static final String PATH = "/api/customers";
    private static final String CUSTOMER_NAME = "Customer name";
    private static final String CUSTOMER_LASTNAME = "Customer lastname";
    private static final String CUSTOMER_EMAIL = "customer@email";
    private static final long PHONE_NUMBER = 9289310L;
    private static final long ID = 1L;

    @Test
    public void shouldFetchEmptyCustomerResponseList() throws Exception {
        //GIVEN
        List<CustomerDto> customersDto = new ArrayList<>();
        List<CustomerResponse> customersResponse = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 1);

        Page<CustomerDto> customersDtoPage = new PageImpl<>(customersDto);
        Page<CustomerResponse> customersResponsePage = new PageImpl<>(customersResponse);

        when(customerFacade.getAllCustomers(pageable)).thenReturn(customersDtoPage);
        when(customerMapper.mapToCustomerResponsePage(customersDtoPage)).thenReturn(customersResponsePage);

        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/customers")))
                .andReturn();
    }

    @Test
    public void shouldFetchGetAllCustomerResponseList() throws Exception {
        //GIVEN
        CustomerDto customerDto = new CustomerDto(
                ID,
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                PHONE_NUMBER,
                CUSTOMER_EMAIL);

        CustomerResponse customerResponse = new CustomerResponse(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber());

        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(customerDto);

        List<CustomerResponse> customersResponse = new ArrayList<>();
        customersResponse.add(customerResponse);

        Pageable pageable = PageRequest.of(0, 1);

        Page<CustomerDto> customersDtoPage = new PageImpl<>(customersDto);
        Page<CustomerResponse> customersResponsePage = new PageImpl<>(customersResponse);

        when(customerFacade.getAllCustomers(pageable)).thenReturn(customersDtoPage);
        when(customerMapper.mapToCustomerResponsePage(customersDtoPage)).thenReturn(customersResponsePage);

        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_embedded.customerResponseList", hasSize(1)))
                .andReturn();
    }

    @Test
    public void shouldFetchShowCustomerById() throws Exception {
        //GIVEN
        CustomerDto customerDto = new CustomerDto(
                ID,
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                PHONE_NUMBER,
                CUSTOMER_EMAIL);

        CustomerResponse customerResponse = new CustomerResponse(
                customerDto.getId(),
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber());

        when(customerFacade.getCustomerById(customerDto.getId())).thenReturn(customerDto);
        when(customerMapper.mapToCustomerResponse(customerDto)).thenReturn(customerResponse);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/" + ID).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(CUSTOMER_NAME)))
                .andExpect(jsonPath("$.lastname", is(CUSTOMER_LASTNAME)))
                .andExpect(jsonPath("$.email", is(CUSTOMER_EMAIL)))
                .andExpect(jsonPath("$.phoneNumber", is(9289310)))
                .andReturn();
    }

    @Test
    public void shouldFetchDeleteCustomer() throws Exception {
        //GIVEN & WHEN & THEN
        mockMvc.perform(delete(PATH + "/" + ID)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldFetchRegisterCustomer() throws Exception {
        //GIVEN
        CustomerDto customerDto = new CustomerDto(
                CUSTOMER_NAME,
                CUSTOMER_LASTNAME,
                PHONE_NUMBER,
                CUSTOMER_EMAIL);

        CustomerResponse customerResponse = new CustomerResponse(
                ID,
                customerDto.getName(),
                customerDto.getLastname(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber());

        when(customerFacade.createCustomer(any())).thenReturn(customerDto);
        when(customerMapper.mapToCustomerResponse(customerDto)).thenReturn(customerResponse);

        Gson gson = new Gson();
        String json = gson.toJson(customerResponse);

        //WHEN & THEN
        mockMvc.perform(post(PATH).contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(json)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastname", is(CUSTOMER_LASTNAME)))
                .andReturn();
    }

    @Test
    public void shouldFetchRemoveProductFromCart() throws Exception {
        //GIVEN
        CartDto cartDto = new CartDto(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                new CustomerDto(),
                Collections.EMPTY_LIST
        );

        CartResponse cartResponse = new CartResponse(
                ID,
                cartDto.getUniqueId(),
                cartDto.isOpen(),
                new CustomerResponse(),
                Collections.EMPTY_LIST
        );

        Gson gson = new Gson();
        String json = gson.toJson(cartResponse);

        when(sessionCartFacade.removeProductFromCart(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(cartDto);
        when(cartMapper.mapToCartResponse(cartDto)).thenReturn(cartResponse);
        //WHEN & THEN
        mockMvc.perform(delete(PATH + "/1/carts/session/product").contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(json)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lineItems", hasSize(0)));
    }
}