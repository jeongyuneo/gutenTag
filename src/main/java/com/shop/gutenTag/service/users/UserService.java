package com.shop.gutenTag.service.users;

import com.shop.gutenTag.domain.user.User;
import com.shop.gutenTag.domain.user.UserRepository;
import com.shop.gutenTag.web.dto.UserFindRequestDto;
import com.shop.gutenTag.web.dto.UserFindResponseDto;
import com.shop.gutenTag.web.dto.UserSaveRequestDto;
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
    public Long createUser(UserSaveRequestDto signupRequestDto) {
        return userRepository.save(signupRequestDto.toEntity()).getId();
    }

    @Transactional
    public String findUser(UserFindRequestDto userFindRequestDto) {
        UserFindResponseDto userFindResponseDto = findById(userFindRequestDto.toEntity().getUserId(), userFindRequestDto.toEntity().getPassword());
        System.out.println(userFindResponseDto.getUserId());
        httpSession.setAttribute("user", userFindResponseDto);
        return userFindResponseDto.getUserId();
    }

    public UserFindResponseDto findById(String userId, String password) {
        try {
            User user = userRepository.findByUserIdAndPassword(userId, password);
            if (user != null) {
                return new UserFindResponseDto(user);
            } else {
                throw new IllegalArgumentException("해당 아이디가 없습니다. id = " + userId);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("해당 아이디가 없습니다. id = " + userId);
        }
    }
    /*@Transactional
    public Long updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {

    }*/
}
