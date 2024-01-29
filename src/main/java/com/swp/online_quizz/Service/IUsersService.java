package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Repository.UsersRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IUsersService implements UsersService {
    @Autowired
    private UsersRepostory usersRepostory;
    @Override
    public List<Users> getAlList() {
        return this.usersRepostory.findAll();
    }


    @Override
    public Boolean create(Users users) {
        try {
            this.usersRepostory.save(users);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public Users findById(Integer userID) {
        return null;
    }

    @Override
    public Boolean update(Users users) {
        return null;
    }

    @Override
    public Boolean delete(Integer userID) {
        return null;
    }
}
