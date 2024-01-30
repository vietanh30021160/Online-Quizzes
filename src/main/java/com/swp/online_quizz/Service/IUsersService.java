package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUsersService {
    public User getUsersByID(Integer userID);
    List<User> getAlList();
    Boolean create(User users);
    User findById(Integer userID);
    Boolean update(User users);
    Boolean delete(Integer userID);
}
