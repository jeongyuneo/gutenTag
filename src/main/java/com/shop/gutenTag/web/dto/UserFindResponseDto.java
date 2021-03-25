package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Getter;

@Getter
public class UserFindResponseDto {

    private Long id;
    private String userId;
    private String password;
    //private String email;

    public UserFindResponseDto(User entity) {
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        //this.email = entity.getEmail();
    }
}
