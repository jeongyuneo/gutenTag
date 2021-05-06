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
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = findById(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (user.getEmail().equals("0")) {
            return new LoginResponseDto();
        }
        System.out.println("[Login] email : " + user.getEmail());
        httpSession.setAttribute("user", user);
        return new LoginResponseDto(userRepository.findById(loginRequestDto.getEmail()));
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
            System.out.println(entity);
            if (entity != null) {
                return entity;
            } else {
                return new User("0", "0", "0", "0", "0");
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
        }
    }
}
