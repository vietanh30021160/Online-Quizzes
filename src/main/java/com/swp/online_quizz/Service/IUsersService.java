package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUsersService {
    public User getUsersByID(Integer userID);
}
