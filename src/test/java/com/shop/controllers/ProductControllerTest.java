package com.shop.controllers;

import com.shop.application.GetProductDetailService;
import com.shop.application.GetProductListService;
import com.shop.dtos.CategoryDto;
import com.shop.dtos.ImageDto;
import com.shop.dtos.ProductDetailDto;
import com.shop.dtos.ProductListDto;
import com.shop.dtos.ProductSummaryDto;
import com.shop.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetProductListService getProductListService;

    @MockBean
    private GetProductDetailService getProductDetailService;


    @Test
    @DisplayName("GET /products")
    void list() throws Exception {
        ProductSummaryDto productDto = new ProductSummaryDto(
                "0BV000CAT0001",
                new CategoryDto("0BV000CAT", "top"),
                new ImageDto("https://shop.com/products/0BV000CAT0001"),
                "Product 1",
                100_100L
        );

        given(getProductListService.getProductListDto(null))
                .willReturn(new ProductListDto(List.of(productDto)));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product 1")));

    }

    @Test
    @DisplayName("GET /products/{id}")
    void detail() throws Exception {
        ProductDetailDto productDto = new ProductDetailDto(
                "0BV000CAT0001",
                new CategoryDto("0BV000CAT", "top"),
                List.of(new ImageDto("https://shop.com/products/0BV000CAT0001")),
                "Product 1",
                100_100L,
                List.of(),
                ""
        );

        given(getProductDetailService.getProductDetailDto("0BV000CAT0001"))
                .willReturn(productDto);

        mockMvc.perform(get("/products/0BV000CAT0001"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Product 1")));

    }
}