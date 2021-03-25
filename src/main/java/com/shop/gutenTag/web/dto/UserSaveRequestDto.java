package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String name;
    private String userId;
    private String password;
    //private String email;

    public UserSaveRequestDto(User entity) {
        this.name = entity.getName();
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        //this.email = entity.getEmail();
    }

    @Builder
    public UserSaveRequestDto(String name, String userId, String password) {
        this.name = name;
        this.userId = userId;
        this.password = password;
        //this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .userId(userId)
                .password(password)
                //.email(email)
                .build();
    }
}
