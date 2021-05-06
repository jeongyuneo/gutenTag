package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
    private String telephone;
    private String address;

    @Builder
    public SignupRequestDto(String name, String email, String password, String telephone, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
    }

    public User toEntitiy() {
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .telephone(telephone)
                .address(address)
                .build();
    }
}
