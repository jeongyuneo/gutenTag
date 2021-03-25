package com.shop.gutenTag.config.auth.dto;

import com.shop.gutenTag.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable{

    private String name;
    private String userId;
    private String password;

    public SessionUser(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }
}
