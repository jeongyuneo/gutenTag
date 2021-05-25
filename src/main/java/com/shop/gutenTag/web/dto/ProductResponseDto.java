package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductResponseDto {
    private Long id;
    private String name;
    private String price;

    public ProductResponseDto(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.price = entity.getPrice();
    }
}
