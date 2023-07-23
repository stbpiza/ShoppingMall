package com.shop;

import com.shop.models.CategoryId;
import com.shop.models.Image;
import com.shop.models.Money;
import com.shop.models.Product;
import com.shop.models.ProductId;
import com.shop.models.ProductOption;
import com.shop.models.ProductOptionId;
import com.shop.models.ProductOptionItem;
import com.shop.models.ProductOptionItemId;

import java.util.List;
import java.util.NoSuchElementException;


public class Fixtures {
    public static Product product(String name) {
        if (name.equals("맨투맨")) {
            return new Product(
                    new ProductId("0BV000PRO0001"),
                    new CategoryId("0BV000CAT0001"),
                    List.of(
                            new Image("http://example.com/product-01.jpg")
                    ),
                    "맨투맨",
                    new Money(128_000L),
                    List.of(
                            productOption("색상"),
                            productOption("크기")
                    ),
                    "편하게 입을 수 있는 맨투맨");
        }

        if (name.equals("셔츠")) {
            return new Product(
                    new ProductId("0BV000PRO0002"),
                    new CategoryId("0BV000CAT0001"),
                    List.of(
                            new Image("http://example.com/product-02.jpg")
                    ),
                    "셔츠",
                    new Money(128_000L),
                    List.of(),
                    "Shirt");
        }

        throw new NoSuchElementException("Product - name: " + name);
    }

    private static ProductOption productOption(String name) {
        if (name.equals("색상")) {
            return new ProductOption(
                    new ProductOptionId("0BV000OPT0001"),
                    "색상",
                    List.of(
                            productOptionItem("Black"),
                            productOptionItem("White")
                    ));
        }

        if (name.equals("크기")) {
            return new ProductOption(
                    new ProductOptionId("0BV000OPT0002"),
                    "크기",
                    List.of(
                            productOptionItem("S"),
                            productOptionItem("M"),
                            productOptionItem("L")
                    ));
        }

        throw new NoSuchElementException("ProductOption - name: " + name);
    }

    private static ProductOptionItem productOptionItem(String name) {
        if (name.equals("Black")) {
            return new ProductOptionItem(
                    new ProductOptionItemId("0BV000ITEM001"),
                    "Black");
        }

        if (name.equals("White")) {
            return new ProductOptionItem(
                    new ProductOptionItemId("0BV000ITEM002"),
                    "White");
        }

        if (name.equals("S")) {
            return new ProductOptionItem(
                    new ProductOptionItemId("0BV000ITEM003"),
                    "S");
        }

        if (name.equals("M")) {
            return new ProductOptionItem(
                    new ProductOptionItemId("0BV000ITEM004"),
                    "M");
        }

        if (name.equals("L")) {
            return new ProductOptionItem(
                    new ProductOptionItemId("0BV000ITEM005"),
                    "L");
        }

        throw new NoSuchElementException("ProductOptionItem - name: " + name);
    }
}
