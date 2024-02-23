package com.swp.online_quizz.Mapper;

import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Entity.User;
import java.time.LocalDateTime;

public class UserMapper {

    public static User toUser(UserRegisterDtoRequest userRegisterDtoRequest, String otp) {
        return User.builder()
                .role(userRegisterDtoRequest.getRole())
                .username(userRegisterDtoRequest.getUsername())
                .email(userRegisterDtoRequest.getEmail())
                .passwordHash(userRegisterDtoRequest.getPassword())
                .joinDate(LocalDateTime.now())
                .firstName(userRegisterDtoRequest.getFirstname())
                .lastName(userRegisterDtoRequest.getLastname())
                .dateOfBirth(userRegisterDtoRequest.getDateofbirth())
                .gender(userRegisterDtoRequest.getGender())
                .isActive(false)
                .otp(otp)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
    }

    public static User toUser(UserRegisterDtoRequest userRegisterDtoRequest) {
        return User.builder()
                .role(userRegisterDtoRequest.getRole())
                .username(userRegisterDtoRequest.getUsername())
                .email(userRegisterDtoRequest.getEmail())
                .passwordHash(userRegisterDtoRequest.getPassword())
                .joinDate(LocalDateTime.now())
                .firstName(userRegisterDtoRequest.getFirstname())
                .lastName(userRegisterDtoRequest.getLastname())
                .dateOfBirth(userRegisterDtoRequest.getDateofbirth())
                .gender(userRegisterDtoRequest.getGender())
                .isActive(false)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
    }
}
