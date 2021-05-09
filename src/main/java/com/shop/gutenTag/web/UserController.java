package com.shop.gutenTag.web;

import com.shop.gutenTag.service.users.UserService;
import com.shop.gutenTag.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        if (userService.signup(signupRequestDto).equals(Long.valueOf(-1))) {
            return "signup-fail";
        }
        return "signup-success";
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        if (loginResponseDto.getId().equals(Long.valueOf(-1))) {
            return new LoginResponseDto();
        }
        return loginResponseDto;
    }

    @GetMapping("/logout/{id}")
    public String logout(@PathVariable String email) {
        return userService.logout(email);
    }
}