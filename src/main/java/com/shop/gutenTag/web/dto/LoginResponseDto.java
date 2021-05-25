package com.shop.gutenTag.web.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private Long id;
    private String email;
    private String name;
    private String telephone;
    private String address;

    public LoginResponseDto(User entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.telephone = entity.getTelephone();
        this.address = entity.getAddress();
    }

    public LoginResponseDto(){
        this.id = Long.valueOf(-1);
        this.email = "0";
        this.name = "0";
        this.telephone = "0";
        this.address = "0";
    }
}
