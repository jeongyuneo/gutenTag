package com.shop.gutenTag.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RecommendResponseDto {
    private String style;
    private List<String> category;

    public RecommendResponseDto() {

    }

    public RecommendResponseDto(String style, List<String> category) {
        this.style = style;
        this.category = category;
    }
}
