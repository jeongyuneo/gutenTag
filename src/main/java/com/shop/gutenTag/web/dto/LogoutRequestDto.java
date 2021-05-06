package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Getter;

@Getter
public class LogoutRequestDto {
    private String email;

    public LogoutRequestDto (String email) {
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .build();
    }
}