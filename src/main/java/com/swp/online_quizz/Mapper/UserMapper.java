package com.swp.online_quizz.Mapper;

import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Entity.User;
import java.time.LocalDateTime;

public class UserMapper {

    public static User toUser(UserRegisterDtoRequest userLoginDtoRequest) {
        return User.builder()
                .role(userLoginDtoRequest.getRole())
                .username(userLoginDtoRequest.getUsername())
                .email(userLoginDtoRequest.getEmail())
                .passwordHash(userLoginDtoRequest.getPassword())
                .joinDate(LocalDateTime.now())
                .isActive(!userLoginDtoRequest.getRole().equals("ROLE_TEACHER"))
                .build();
    }
}
