package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;

public class UserFindRequestDto {

    private String userId;
    private String password;

    public UserFindRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .password(password)
                .build();
    }
}
