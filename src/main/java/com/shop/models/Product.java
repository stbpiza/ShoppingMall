package com.shop.models;

import com.shop.dtos.admin.AdminUpdateProductDto;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @EmbeddedId
    private ProductId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "category_id"))
    private CategoryId categoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<Image> images = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price"))
    private Money price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @OrderBy("id")
    private List<ProductOption> options = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "hidden")
    private boolean hidden;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Product() {
    }

    public Product(ProductId productId, CategoryId categoryId, List<Image> images,
                   String name, Money price, List<ProductOption> options,
                   String description) {
        this.id = productId;
        this.categoryId = categoryId;
        this.images = new ArrayList<>(images);
        this.name = name;
        this.price = price;
        this.options = new ArrayList<>(options);
        this.description = description;
    }

    public CategoryId categoryId() {
        return categoryId;
    }
    public ProductId id() {
        return id;
    }

    public Money price() {
        return price;
    }

    public String name() {
        return name;
    }

    public Image image(int index) {
        return images.get(index);
    }

    public List<Image> images() {
        return new ArrayList<>(images);
    }

    public List<ProductOption> options() {
        return new ArrayList<>(options);
    }

    public String description() {
        return description;
    }

    public int optionSize() {
        return options.size();
    }

    public boolean hidden() {
        return hidden;
    }

    public ProductOption option(int index) {
        return options.get(index);
    }

    public int imageSize() {
        return images.size();
    }

    public ProductOption optionById(ProductOptionId optionId) {
        return options.stream()
                .filter(option -> Objects.equals(option.id(), optionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션을 찾을 수 없습니다."));
    }

    public void update(AdminUpdateProductDto productDto) {
        this.categoryId = new CategoryId(productDto.categoryId());

        updateImages(productDto.images());

        this.name = productDto.name();

        this.price = new Money(productDto.price());

        updateOptions(productDto.options());

        this.description = productDto.description();

        this.hidden = productDto.hidden();
    }

    private void updateImages(List<AdminUpdateProductDto.ImageDto> images) {
        this.images.removeIf(image -> {
            String imageId = image.id().toString();
            return images.stream().noneMatch(i -> imageId.equals(i.id()));
        });

        images.forEach(image -> {
            if (image.id() == null) {
                this.images.add(new Image(
                        ImageId.generate(),
                        image.url()
                ));
                return;
            }
            this.images.stream()
                    .filter(i -> i.id().toString().equals(image.id()))
                    .forEach(i -> i.changeUrl(image.url()));
        });
    }

    private void updateOptions(List<AdminUpdateProductDto.OptionDto> options) {
        this.options.removeIf(option -> {
            String optionId = option.id().toString();
            return options.stream().noneMatch(i -> optionId.equals(i.id()));
        });

        options.forEach(option -> {
            if (option.id() == null) {
                this.options.add(new ProductOption(
                        ProductOptionId.generate(),
                        option.name(),
                        option.items().stream()
                                .map(item -> new ProductOptionItem(
                                        ProductOptionItemId.generate(),
                                        item.name()
                                ))
                                .toList()
                ));
                return;
            }
            this.options.stream()
                    .filter(i -> i.id().toString().equals(option.id()))
                    .forEach(i -> {
                        i.changeName(option.name());
                        i.updateItems(option.items());
                    });
        });
    }
}

