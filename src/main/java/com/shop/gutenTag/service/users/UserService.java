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
        if (userRepository.findByEmail(requestDto.toEntitiy().getEmail()) != null) {
            return Long.valueOf(-1);
        }
        return userRepository.save(requestDto.toEntitiy()).getId();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = findByIdAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        if (user.getEmail().equals("0")) {
            return new LoginResponseDto();
        }
        httpSession.setAttribute("user", user);
        return new LoginResponseDto(userRepository.findByEmail(loginRequestDto.getEmail()));
    }

    @Transactional
    public String logout(String email) {
        try {
            User entity = userRepository.findByEmail(email);
            System.out.println("Logout : " + entity.getEmail());
            httpSession.removeAttribute("user");
            return "/logout";
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
        }
    }

    public User findByIdAndPassword(String email, String password) {
        try {
            User entityWithEmail = userRepository.findByEmail(email);
            User entityWithEmailAndPassword = userRepository.findByUserIdAndPassword(email, password);
            if (entityWithEmail == null) {
                return new User("0", "0", "0", "0", "0");
            } else if (entityWithEmailAndPassword == null) {
                return new User("1", "0", "0", "0", "0");
            } else {
                return entityWithEmailAndPassword;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. userId = " + email);
        }
    }
}
