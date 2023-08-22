package com.shop.controllers.admin;

import com.shop.application.CreateProductService;
import com.shop.application.GetProductDetailService;
import com.shop.application.GetProductListService;
import com.shop.application.UpdateProductService;
import com.shop.dtos.admin.AdminCreateProductDto;
import com.shop.dtos.admin.AdminProductDetailDto;
import com.shop.dtos.admin.AdminProductListDto;
import com.shop.dtos.admin.AdminUpdateProductDto;
import com.shop.models.CategoryId;
import com.shop.models.Image;
import com.shop.models.ImageId;
import com.shop.models.Money;
import com.shop.models.ProductId;
import com.shop.models.ProductOption;
import com.shop.models.ProductOptionId;
import com.shop.models.ProductOptionItem;
import com.shop.models.ProductOptionItemId;
import com.shop.security.AdminRequired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AdminRequired
@RestController
@RequestMapping("/admin/products")
public class AdminProductController {
    private final GetProductListService getProductListService;
    private final GetProductDetailService getProductDetailService;
    private final CreateProductService createProductService;
    private final UpdateProductService updateProductService;

    public AdminProductController(GetProductListService getProductListService,
                                  GetProductDetailService getProductDetailService,
                                  CreateProductService createProductService,
                                  UpdateProductService updateProductService) {
        this.getProductListService = getProductListService;
        this.getProductDetailService = getProductDetailService;
        this.createProductService = createProductService;
        this.updateProductService = updateProductService;
    }

    @GetMapping
    public AdminProductListDto list() {
        return getProductListService.getAdminProductListDto();
    }

    @GetMapping("/{id}")
    public AdminProductDetailDto detail(@PathVariable String id) {
        ProductId productId = new ProductId(id);
        return getProductDetailService.getAdminProductDetailDto(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody AdminCreateProductDto productDto) {
        createProductService.createProduct(
                new CategoryId(productDto.categoryId()),
                mapToImages(productDto.images()),
                productDto.name(),
                new Money(productDto.price()),
                mapToProductOptions(productDto.options()),
                productDto.description()
        );
        return "Created";
    }

    @PatchMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @RequestBody AdminUpdateProductDto productDto) {
        updateProductService.updateProduct(new ProductId(id), productDto);
        return "Updated";
    }

    private List<Image> mapToImages(
            List<AdminCreateProductDto.ImageDto> imageDtos) {
        return imageDtos.stream()
                .map(image -> new Image(ImageId.generate(), image.url()))
                .toList();
    }

    private List<ProductOption> mapToProductOptions(
            List<AdminCreateProductDto.OptionDto> optionDtos) {
        return optionDtos.stream()
                .map(option -> new ProductOption(
                        ProductOptionId.generate(),
                        option.name(),
                        mapToProductOptionItems(option.items())
                ))
                .toList();
    }

    private List<ProductOptionItem> mapToProductOptionItems(
            List<AdminCreateProductDto.OptionItemDto> optionItemDtos) {
        return optionItemDtos.stream()
                .map(optionItem -> new ProductOptionItem(
                        ProductOptionItemId.generate(),
                        optionItem.name()
                ))
                .toList();
    }
}
