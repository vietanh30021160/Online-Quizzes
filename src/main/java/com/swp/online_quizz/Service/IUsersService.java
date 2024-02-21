package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

import com.swp.online_quizz.Entity.User;

public interface IUsersService {
    public User getUsersByID(Integer userID);

    List<User> getAlList();

    List<User> getUserIsActive();

    List<User> findIsactiveTeachers();

    void toggleActive(Integer userId);

    List<User> getTeachers();

    List<User> getStudent();


    List<User> searchByUsername(String username);

    List<User> searchByUsernameStudent(String username);


    boolean updateUser(Integer userId, User updatedUser);

    Boolean create(User users);

    User findById(Integer userID);
    Boolean update(User users);
    Boolean delete(Integer userID);
}
