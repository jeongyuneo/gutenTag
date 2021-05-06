package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String email;
    private String name;
    private String telephone;
    private String address;

    public LoginResponseDto(User entity){
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.telephone = entity.getTelephone();
        this.address = entity.getAddress();
    }
}