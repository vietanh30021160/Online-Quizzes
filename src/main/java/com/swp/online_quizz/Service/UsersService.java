package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements IUsersService{
    @Autowired
    UsersRepository usersRepository;
    @Override
    public Users getUsersByID(Integer userID) {
        return usersRepository.getReferenceById(userID);
    }
}
