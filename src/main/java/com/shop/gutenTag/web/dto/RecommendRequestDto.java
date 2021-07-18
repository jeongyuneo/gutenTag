package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.product.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RecommendRequestDto {
    private String minorCategory;

    public RecommendRequestDto() {

    }

    public RecommendRequestDto(String minorCategory) {
        this.minorCategory = minorCategory;
    }

    public Product toEntity() {
        return Product
                .builder()
                .category(minorCategory)
                .build();
    }
}
