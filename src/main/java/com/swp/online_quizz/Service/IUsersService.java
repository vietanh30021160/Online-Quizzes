package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.User;

public interface IUsersService {
    public User getUsersByID(Integer userID);

    List<User> getAlList();

    Boolean create(User users);

    User findById(Integer userID);

    Boolean update(User users);

    Boolean delete(Integer userID);
}
