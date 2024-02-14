package com.swp.online_quizz.Service;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Mapper.UserMapper;
import com.swp.online_quizz.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean login(UserLoginDtoRequest userLoginDtoRequest) {
        Optional<User> userOptional = usersRepository.findByUsername(userLoginDtoRequest.getUsername());
        if(userOptional.isEmpty()){
            return false;
        }else{
            return passwordEncoder.matches(userLoginDtoRequest.getPassword(), userOptional.get().getPasswordHash());
        }
    }

    @Override
    public boolean register(UserRegisterDtoRequest userRegisterDtoRequest) {
        Optional<User> checkUsername = usersRepository.findByUsername(userRegisterDtoRequest.getUsername());
        String checkEmail = usersRepository.findByEmail(userRegisterDtoRequest.getEmail());
        if(checkUsername.isEmpty ()&& checkEmail==null){
            userRegisterDtoRequest.setPassword(passwordEncoder.encode(userRegisterDtoRequest.getPassword()));
            usersRepository.save(UserMapper.toUser(userRegisterDtoRequest));
            return true;
        }
        return false;
    }

}
