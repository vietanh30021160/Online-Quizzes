package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UsersService {
    List<Users> getAlList();
    Boolean create(Users users);
    Users findById(Integer userID);
    Boolean update(Users users);
    Boolean delete(Integer userID);
}
