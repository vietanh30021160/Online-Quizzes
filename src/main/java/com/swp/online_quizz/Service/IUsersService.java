package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface IUsersService {
    public Users getUsersByID(Integer userID);
}
