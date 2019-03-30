package com.restaurant.management.web.controller;

import com.google.gson.Gson;
import com.restaurant.management.domain.dto.CartDto;
import com.restaurant.management.domain.dto.CustomerDto;
import com.restaurant.management.mapper.CartMapper;
import com.restaurant.management.service.facade.CartFacade;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CartController.class, secure = false)
public class CartControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartFacade cartFacade;
    @MockBean
    private CartMapper cartMapper;

    private static final String PATH = "/api/carts";
    private static final String CART_UNIQUE_ID = "uniqueId";
    private static final long ID = 1L;

    @Test
    public void shouldFetchShowAllCarts() throws Exception {
        //GIVEN
        CartDto cartDto = new CartDto(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                new CustomerDto(),
                new ArrayList<>()
        );

        CartResponse cartResponse = new CartResponse(
                ID,
                cartDto.getUniqueId(),
                cartDto.isOpen(),
                new CustomerResponse(),
                new ArrayList<>()
        );

        List<CartDto> cartsDto = new ArrayList<>();
        cartsDto.add(cartDto);

        List<CartResponse> cartsResponse = new ArrayList<>();
        cartsResponse.add(cartResponse);

        Pageable pageable = PageRequest.of(0, 1);

        Page<CartDto> cartsDtoPage = new PageImpl<>(cartsDto);
        Page<CartResponse> cartsResponsePage = new PageImpl<>(cartsResponse);

        when(cartFacade.getAllCarts(pageable)).thenReturn(cartsDtoPage);
        when(cartMapper.mapToCartResponsePage(cartsDtoPage)).thenReturn(cartsResponsePage);
        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cartResponseList", hasSize(1)))
                .andReturn();
    }

    @Test
    public void shouldFetchShowCartByUniqueId() throws Exception {
        //GIVEN
        CartDto cartDto = new CartDto(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                new CustomerDto(),
                new ArrayList<>()
        );

        CartResponse cartResponse = new CartResponse(
                ID,
                cartDto.getUniqueId(),
                cartDto.isOpen(),
                new CustomerResponse(),
                new ArrayList<>()
        );

        when(cartFacade.getCartByUniqueId(cartDto.getUniqueId())).thenReturn(cartDto);
        when(cartMapper.mapToCartResponse(cartDto)).thenReturn(cartResponse);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/" + CART_UNIQUE_ID).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId", is(CART_UNIQUE_ID)))
                .andExpect(jsonPath("$.open", is(Boolean.FALSE)))
                .andReturn();
    }

    @Test
    public void shouldFetchShowAllSessionCarts() throws Exception {
        //GIVEN
        CartDto cartDto = new CartDto(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                new CustomerDto(),
                new ArrayList<>()
        );

        CartResponse cartResponse = new CartResponse(
                ID,
                cartDto.getUniqueId(),
                cartDto.isOpen(),
                new CustomerResponse(),
                new ArrayList<>()
        );

        List<CartDto> cartsDto = new ArrayList<>();
        cartsDto.add(cartDto);

        List<CartResponse> cartsResponse = new ArrayList<>();
        cartsResponse.add(cartResponse);

        Pageable pageable = PageRequest.of(0, 1);

        Page<CartDto> cartsDtoPage = new PageImpl<>(cartsDto);
        Page<CartResponse> cartsResponsePage = new PageImpl<>(cartsResponse);

        when(cartFacade.getSessionCarts(pageable)).thenReturn(cartsDtoPage);
        when(cartMapper.mapToCartResponsePage(cartsDtoPage)).thenReturn(cartsResponsePage);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/session")
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.cartResponseList", hasSize(1)))
                .andReturn();
    }

    @Test
    public void shouldFetchShowSessionCartByUniqueId() throws Exception {
        //GIVEN
        CartDto cartDto = new CartDto(
                CART_UNIQUE_ID,
                Boolean.FALSE,
                new CustomerDto(),
                new ArrayList<>()
        );

        CartResponse cartResponse = new CartResponse(
                ID,
                cartDto.getUniqueId(),
                cartDto.isOpen(),
                new CustomerResponse(),
                new ArrayList<>()
        );

        when(cartFacade.getSessionCartByUniqueId(cartDto.getUniqueId())).thenReturn(cartDto);
        when(cartMapper.mapToCartResponse(cartDto)).thenReturn(cartResponse);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/session/" + CART_UNIQUE_ID).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId", is(CART_UNIQUE_ID)))
                .andExpect(jsonPath("$.open", is(Boolean.FALSE)))
                .andReturn();
    }

    @Test
    public void shouldFetchDeleteSessionCartByUniqueId() throws Exception {
        //GIVEN & WHEN & THEN
        mockMvc.perform(delete(PATH + "/session/" + CART_UNIQUE_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }


}