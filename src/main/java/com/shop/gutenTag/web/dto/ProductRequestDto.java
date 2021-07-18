package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String category;

    public ProductRequestDto() {

    }

    public ProductRequestDto(String category) {
        this.category = category;
    }

    public Product toEntity() {
        return Product
                .builder()
                .category(category)
                .build();
    }
}
