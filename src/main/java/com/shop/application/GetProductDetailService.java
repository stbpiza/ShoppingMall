package com.shop.application;

import com.shop.dtos.ProductDetailDto;
import com.shop.dtos.admin.AdminProductDetailDto;
import com.shop.models.Category;
import com.shop.models.CategoryId;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.repositories.CategoryRepository;
import com.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductDetailService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public GetProductDetailService(ProductRepository productRepository,
                                   CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDetailDto getProductDetailDto(String productId) {
        ProductId id = new ProductId(productId);
        Product product = productRepository.findById(id)
                .orElseThrow();

        Category category = categoryRepository
                .findById(product.categoryId())
                .orElseThrow();
        return ProductDetailDto.of(product, category);
    }

    private List<Product> findProducts(String categoryId) {
        return categoryId == null
                ? productRepository.findAll()
                : productRepository.findAllByCategoryId(new CategoryId(categoryId));
    }

    public AdminProductDetailDto getAdminProductDetailDto(ProductId productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        Category category = categoryRepository
                .findById(product.categoryId())
                .orElseThrow();

        return AdminProductDetailDto.of(product, category);
    }
}
