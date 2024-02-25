package com.swp.online_quizz.Service;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Mapper.UserMapper;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Utill.EmailUtil;
import com.swp.online_quizz.Utill.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailUtil emailUtil;
    private final OtpUtil otpUtil;
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
        String checkEmail = usersRepository.findEmailByEmailIgnoreCase(userRegisterDtoRequest.getEmail());
        if (checkUsername.isEmpty() && checkEmail == null){
            if (userRegisterDtoRequest.getRole().equals("ROLE_STUDENT")) {
                String otp = otpUtil.generateOtp();
                try {
                    emailUtil.sendOtpEmail(userRegisterDtoRequest.getEmail(), otp);
                } catch (MessagingException e) {
                    System.out.println("Unable to send otp please try again");
                    return false;
                }

                userRegisterDtoRequest.setPassword(passwordEncoder.encode(userRegisterDtoRequest.getPassword()));
                usersRepository.save(UserMapper.toUser(userRegisterDtoRequest, otp));
                return true;

            } else {
                    userRegisterDtoRequest.setPassword(passwordEncoder.encode(userRegisterDtoRequest.getPassword()));
                    usersRepository.save(UserMapper.toUser(userRegisterDtoRequest));
                    return true;
            }
    }
        return false;
    }

    @Override
    public boolean verifyAccount(String email, String otp) {
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if(userOptional.isEmpty()){
            System.out.println("User not found");
            return false;
        }
        User user = userOptional.get();
        if(user.getOtp().equals(otp)&& Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds()<(5*60)&&user.getRole().equals("ROLE_STUDENT")){
            user.setIsActive(true);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean regenerateOtp(String email) {
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if(userOptional.isEmpty()){
            System.out.println("User not found");
            return false;
        }
        User user = userOptional.get();
        if(user.getRole().equals("ROLE_STUDENT")) {
            String otp = otpUtil.generateOtp();
            try {
                emailUtil.sendOtpEmail(email, otp);
            } catch (MessagingException e) {
                System.out.println("Unable to send otp please try again");
                return false;
            }
            user.setOtp(otp);
            user.setOtpGeneratedTime(LocalDateTime.now());
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Boolean forgotPassword(String email) {
        if(email==null || email.isEmpty()){
            return false;
        }
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            System.out.println("Unable to send otp please try again");
            return false;
        }
        return true;
    }

    @Override
    public boolean setPassword(String email, String newPassword) {
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if(userOptional.isEmpty()){
            System.out.println("User not found");
            return false;
        }
        User user = userOptional.get();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
        String subject = "Change password";
        String text = "Your account's password has recently been changed";
        try {
            emailUtil.sendNotication(email,subject,text);
        } catch (MessagingException e) {
            System.out.println("Unable to send otp please try again");
            return false;
        }
        return true;
    }

}
