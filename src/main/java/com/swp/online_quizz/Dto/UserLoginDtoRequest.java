package com.swp.online_quizz.Dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserLoginDtoRequest {
    String password;
    String username;
}
