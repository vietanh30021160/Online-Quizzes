package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;

public interface IUserService {
    boolean login(UserLoginDtoRequest userLoginDtoRequest);

    boolean register(UserRegisterDtoRequest userRegisterDtoRequest);

}
