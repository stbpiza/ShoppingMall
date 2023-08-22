package com.shop.application;

import com.shop.models.CategoryId;
import com.shop.models.Image;
import com.shop.models.Money;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.models.ProductOption;
import com.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateProductService {
    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(
            CategoryId categoryId, List<Image> images,
            String name, Money price, List<ProductOption> options,
            String description) {
        ProductId id = ProductId.generate();

        Product product = new Product(
                id, categoryId, images, name, price, options, description);

        productRepository.save(product);

        return product;
    }
}
