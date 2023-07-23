package com.shop.application;

import com.shop.models.Cart;
import com.shop.models.CartId;
import com.shop.models.CartLineItemOption;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.models.UserId;
import com.shop.repositories.CartRepository;
import com.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AddProductToCartService {
    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public AddProductToCartService(CartRepository cartRepository,
                                   ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void addProductToCart(UserId userId, ProductId productId,
                                 Set<CartLineItemOption> options, int quantity) {
        // 장바구니가 없으면 장바구니 객체를 만들고, 나중에 save를 해준다.
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart(CartId.generate(), userId));

        Product product = productRepository.findById(productId)
                .orElseThrow();

        cart.addProduct(product, options, quantity);

        cartRepository.save(cart);
    }
}
