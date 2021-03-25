package com.shop.gutenTag.web;

import com.shop.gutenTag.service.users.UserService;
import com.shop.gutenTag.web.dto.UserFindRequestDto;
import com.shop.gutenTag.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final UserService userService;

    @PostMapping("/signup")
    public Long createUser(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.createUser(userSaveRequestDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserFindRequestDto userFindRequestDto) {
        return userService.findUser(userFindRequestDto);
    }
}
