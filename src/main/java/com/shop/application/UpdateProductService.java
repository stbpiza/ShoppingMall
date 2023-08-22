package com.shop.application;

import com.shop.dtos.admin.AdminUpdateProductDto;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductService {
    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void updateProduct(
            ProductId productId, AdminUpdateProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.update(productDto);
    }
}
