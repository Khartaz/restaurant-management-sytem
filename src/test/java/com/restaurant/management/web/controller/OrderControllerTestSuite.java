package com.restaurant.management.web.controller;

import com.google.gson.Gson;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.domain.dto.OrderDto;
import com.restaurant.management.mapper.OrderMapper;
import com.restaurant.management.service.facade.OrderFacade;
import com.restaurant.management.web.response.CartResponse;
import com.restaurant.management.web.response.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
public class OrderControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderMapper orderMapper;
    @MockBean
    private OrderFacade orderFacade;

    private static final String PATH = "/api/orders";
    private static final String ORDER_NUMBER = "FJ2J9J";
    private static final String ORDER_STATUS = "ORDERED";
    private static final Double TOTAL_PRICE = 850.20;
    private static final long ID = 1L;

    @Test
    public void shouldFetchShowOrders() throws Exception {
        //GIVEN
        OrderDto orderDto = new OrderDto(
                ORDER_NUMBER,
                Calendar.getInstance(),
                ORDER_STATUS,
                TOTAL_PRICE,
                new CartDto()
        );

        OrderResponse response = new OrderResponse(
                ID,
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                new CartResponse()
        );

        List<OrderDto> ordersDto = new ArrayList<>();
        ordersDto.add(orderDto);

        List<OrderResponse> ordersResponse = new ArrayList<>();
        ordersResponse.add(response);

        Pageable pageable = PageRequest.of(0, 1);

        Page<OrderDto> ordersDtoPage = new PageImpl<>(ordersDto);
        Page<OrderResponse> ordersResponsePage = new PageImpl<>(ordersResponse);

        when(orderFacade.getAllOrders(pageable)).thenReturn(ordersDtoPage);
        when(orderMapper.mapToOrderResponsePage(ordersDtoPage)).thenReturn(ordersResponsePage);
        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.orderResponseList", hasSize(1)))
                .andReturn();
    }

    @Test
    public void shouldFetchShowOrderByOrderNumber() throws Exception {
        //GIVEN
        OrderDto orderDto = new OrderDto(
                ORDER_NUMBER,
                Calendar.getInstance(),
                ORDER_STATUS,
                TOTAL_PRICE,
                new CartDto()
        );

        OrderResponse orderResponse = new OrderResponse(
                ID,
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                new CartResponse()
        );

        when(orderFacade.getByOrderNumber(ORDER_NUMBER)).thenReturn(orderDto);
        when(orderMapper.mapToOrderResponse(orderDto)).thenReturn(orderResponse);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/" + ORDER_NUMBER).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber", is(ORDER_NUMBER)))
                .andExpect(jsonPath("$.status", is(ORDER_STATUS)))
                .andExpect(jsonPath("$.totalPrice", is(TOTAL_PRICE)))
                .andReturn();
    }

    @Test
    public void shouldFetchRegisterOrder() throws Exception {
        //GIVEN
        OrderDto orderDto = new OrderDto(
                ORDER_NUMBER,
                Calendar.getInstance(),
                ORDER_STATUS,
                TOTAL_PRICE,
                new CartDto()
        );

        OrderResponse orderResponse = new OrderResponse(
                ID,
                orderDto.getOrderNumber(),
                orderDto.getOrdered(),
                orderDto.getStatus(),
                orderDto.getTotalPrice(),
                new CartResponse()
        );

        when(orderFacade.processOrder(any())).thenReturn(orderDto);
        when(orderMapper.mapToOrderResponse(orderDto)).thenReturn(orderResponse);

        Gson gson = new Gson();
        String json = gson.toJson(orderResponse);

        //WHEN & THEN
        mockMvc.perform(post(PATH).contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(json)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber", is(ORDER_NUMBER)))
                .andExpect(jsonPath("$.status", is(ORDER_STATUS)))
                .andExpect(jsonPath("$.totalPrice", is(TOTAL_PRICE)))
                .andReturn();
    }

    @Test
    public void shouldFetchDeleteOrderByOrderNumber() throws Exception {
        //GIVEN & WHEN & THEN
        mockMvc.perform(delete(PATH + "/" + ORDER_NUMBER)
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andReturn();
    }
}