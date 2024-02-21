package com.swp.online_quizz.Dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterDtoRequest {
    String username;
    String password;
    String email;
    String role;
    String firstname;
    String lastname;
    LocalDate dateofbirth;
    String gender;
}
