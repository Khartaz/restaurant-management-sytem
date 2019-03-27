package com.restaurant.management.web.controller;

import com.google.gson.Gson;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CustomerMapper;
import com.restaurant.management.service.facade.CustomerFacade;
import com.restaurant.management.web.response.CustomerResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
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
        List<CustomerResponse> customerResponse = new ArrayList<>();

        when(customerFacade.getAllCustomers()).thenReturn(customersDto);
        when(customerMapper.mapToCustomerResponseList(customersDto)).thenReturn(customerResponse);

        //WHEN & THEN
        mockMvc.perform(get(PATH).contentType(APPLICATION_JSON_VALUE))
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

        List<CustomerResponse> responseList = new ArrayList<>();
        responseList.add(customerResponse);

        when(customerFacade.getAllCustomers()).thenReturn(customersDto);
        when(customerMapper.mapToCustomerResponseList(customersDto)).thenReturn(responseList);

        //WHEN & THEN
        mockMvc.perform(get(PATH).contentType(APPLICATION_JSON_VALUE))
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
}