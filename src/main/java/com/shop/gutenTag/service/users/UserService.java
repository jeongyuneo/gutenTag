package com.shop.gutenTag.service.users;

import com.shop.gutenTag.domain.user.UserRepository;
import com.shop.gutenTag.web.dto.*;
import com.shop.gutenTag.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    public Long signup(SignupRequestDto requestDto) {
        if (userRepository.findById(requestDto.toEntitiy().getEmail()) != null) {
            return Long.valueOf(-1);
        }
        return userRepository.save(requestDto.toEntitiy()).getId();
    }

    @Transactional
    public User login(LoginRequestDto requestDto) {
        LoginResponseDto loginResponseDto = new LoginResponseDto(findById(requestDto.toEntity().getEmail(), requestDto.toEntity().getPassword()));
        System.out.println("Login : " + loginResponseDto.getEmail());
        httpSession.setAttribute("user", loginResponseDto);
        return userRepository.findByUserId(loginResponseDto.getId());
    }

    @Transactional
    public String logout(String email) {
        try {
            User entity = userRepository.findById(email);
            System.out.println("Logout : " + entity.getEmail());
            httpSession.removeAttribute("user");
            return "/logout";
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
        }
    }

    public User findById(String email, String password) {
        try {
            User entity = userRepository.findByUserIdAndPassword(email, password);
            if (entity != null) {
                return entity;
            } else {
                throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
        }
    }
}
