package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService implements IUsersService {
    @Autowired
    UsersRepository usersRepository;
    @Override
    public List<User> getAlList() {
        return this.usersRepository.findAll();
    }


    @Override
    public Boolean create(User users) {
        try {
            this.usersRepository.save(users);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public User findById(Integer userID) {
        return null;
    }

    @Override
    public Boolean update(User users) {
        return null;
    }

    @Override
    public Boolean delete(Integer userID) {
        return null;
    }
    @Override
    public User getUsersByID(Integer userID) {
        return usersRepository.getReferenceById(userID);
    }
}
