package com.restaurant.management.web.controller;

import com.google.gson.Gson;
import com.restaurant.management.domain.dto.ProductDto;
import com.restaurant.management.mapper.ProductMapper;
import com.restaurant.management.service.facade.ProductFacade;
import com.restaurant.management.web.response.ProductResponse;
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
import java.util.Date;
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
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductFacade productFacade;
    @MockBean
    private ProductMapper productMapper;

    private static final String PATH = "/api/products";

    private static final String UNIQUE_ID = "JD3ND2E";
    private static final String PRODUCT_NAME = "Product name";
    private static final String PRODUCT_CATEGORY = "Product category";
    private static final Double PRODUCT_PRICE = 2.20;

    @Test
    public void shouldFetchRegisterProduct() throws Exception {
        //GIVEN
        ProductDto productDto = new ProductDto(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                new ArrayList<>()
        );

        ProductResponse response = new ProductResponse(
                1L,
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                new Date().toInstant(),
                new ArrayList<>()
        );

        when(productFacade.registerProduct(any())).thenReturn(productDto);
        when(productMapper.mapToProductResponse(productDto)).thenReturn(response);

        Gson gson = new Gson();
        String json = gson.toJson(response);

        //WHEN & THEN
        mockMvc.perform(post(PATH).contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(json)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId", is(UNIQUE_ID)))
                .andExpect(jsonPath("$.name", is(PRODUCT_NAME)))
                .andExpect(jsonPath("$.category", is(PRODUCT_CATEGORY)))
                .andExpect(jsonPath("$.price", is(PRODUCT_PRICE)))
                .andReturn();
    }

    @Test
    public void shouldFetchEmptyProductResponseList() throws Exception {
        //GIVEN
        List<ProductDto> productsDto = new ArrayList<>();
        List<ProductResponse> productsResponse = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 1);

        Page<ProductDto> productsDtoPage = new PageImpl<>(productsDto);
        Page<ProductResponse> productsResponsePage = new PageImpl<>(productsResponse);

        when(productFacade.getAllProducts(pageable)).thenReturn(productsDtoPage);
        when(productMapper.mapToProductResponsePage(productsDtoPage)).thenReturn(productsResponsePage);
        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/api/products")))
                .andReturn();
    }

    @Test
    public void shouldFetchShowAllProducts() throws Exception {
        //GIVEN
        ProductDto productDto = new ProductDto(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                new ArrayList<>()
        );

        ProductResponse response = new ProductResponse(
                1L,
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                new Date().toInstant(),
                new ArrayList<>()
        );

        List<ProductDto> productsDto = new ArrayList<>();
        productsDto.add(productDto);

        List<ProductResponse> productsResponse = new ArrayList<>();
        productsResponse.add(response);

        Pageable pageable = PageRequest.of(0, 1);

        Page<ProductDto> productsDtoPage = new PageImpl<>(productsDto);
        Page<ProductResponse> productsResponsePage = new PageImpl<>(productsResponse);

        when(productFacade.getAllProducts(pageable)).thenReturn(productsDtoPage);
        when(productMapper.mapToProductResponsePage(productsDtoPage)).thenReturn(productsResponsePage);
        //WHEN & THEN
        mockMvc.perform(get(PATH)
                .param("page", "0")
                .param("size", "1")
                .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.productResponseList", hasSize(1)))
                .andReturn();
    }

    @Test
    public void shouldFetchUpdateProduct() throws Exception {
        //GIVEN
        ProductDto productDto = new ProductDto(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                new ArrayList<>()
        );

        ProductResponse response = new ProductResponse(
                1L,
                productDto.getUniqueId(),
                productDto.getName(),
                "product category updated",
                productDto.getPrice(),
                new Date().toInstant(),
                new ArrayList<>()
        );

        Gson gson = new Gson();
        String json = gson.toJson(response);

        when(productFacade.updateProduct(any())).thenReturn(productDto);
        when(productMapper.mapToProductResponse(productDto)).thenReturn(response);

        //WHEN & THEN
        mockMvc.perform(put(PATH).contentType(APPLICATION_JSON_VALUE)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(json)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is("product category updated")))
                .andReturn();
    }

    @Test
    public void shouldFetchShowProductByUniqueId() throws Exception {
        //GIVEN
        ProductDto productDto = new ProductDto(
                UNIQUE_ID,
                PRODUCT_NAME,
                PRODUCT_CATEGORY,
                PRODUCT_PRICE,
                new ArrayList<>()
        );

        ProductResponse response = new ProductResponse(
                1L,
                productDto.getUniqueId(),
                productDto.getName(),
                productDto.getCategory(),
                productDto.getPrice(),
                new Date().toInstant(),
                new ArrayList<>()
        );

        when(productFacade.getProductByUniqueId(productDto.getUniqueId())).thenReturn(productDto);
        when(productMapper.mapToProductResponse(productDto)).thenReturn(response);
        //WHEN & THEN
        mockMvc.perform(get(PATH + "/" + UNIQUE_ID).contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uniqueId", is(UNIQUE_ID)))
                .andExpect(jsonPath("$.name", is(PRODUCT_NAME)))
                .andExpect(jsonPath("$.category", is(PRODUCT_CATEGORY)))
                .andExpect(jsonPath("$.price", is(PRODUCT_PRICE)))
                .andReturn();
    }

    @Test
    public void shouldFetchDeleteProductByUniqueId() throws Exception {
        //GIVEN & WHEN & THEN
        mockMvc.perform(delete(PATH + "/" + UNIQUE_ID)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}