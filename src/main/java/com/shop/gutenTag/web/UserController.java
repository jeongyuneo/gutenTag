package com.shop.gutenTag.web;

import com.shop.gutenTag.domain.user.UserRepository;
import com.shop.gutenTag.service.users.UserService;
import com.shop.gutenTag.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/signup/{id}")
    public String signup(@RequestBody SignupRequestDto signupRequestDto) {
        if (userRepository.findByUserId(userService.signup(signupRequestDto)) != null) {
            return "signup-fail";
        }
        return "signup-success";
    }

    @PostMapping("/login/{id}")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return new LoginResponseDto(userService.login(loginRequestDto));
    }

    @GetMapping("/logout/{id}")
    public String logout(@RequestBody LogoutRequestDto logoutRequestDto) {
        return userService.logout(logoutRequestDto.getEmail());
    }
}