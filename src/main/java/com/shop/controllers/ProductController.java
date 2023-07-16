package com.shop.controllers;

import com.shop.application.GetProductDetailService;
import com.shop.application.GetProductListService;
import com.shop.dtos.ProductDetailDto;
import com.shop.dtos.ProductListDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final GetProductListService getProductListService;
    private final GetProductDetailService getProductDetailService;

    public ProductController(GetProductListService getProductListService,
                             GetProductDetailService getProductDetailService) {
        this.getProductListService = getProductListService;
        this.getProductDetailService = getProductDetailService;
    }

    @GetMapping
    public ProductListDto list(
            @RequestParam(required = false) String categoryId
    ) {
        return getProductListService.getProductListDto(categoryId);
    }

    @GetMapping("/{id}")
    public ProductDetailDto detail(@PathVariable String id) {
        return getProductDetailService.getProductDetailDto(id);
    }
}
