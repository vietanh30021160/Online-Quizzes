package com.swp.online_quizz.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Mapper.UserMapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.swp.online_quizz.Utill.EmailUtil;
import com.swp.online_quizz.Utill.OtpUtil;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;

@Service
public class UsersService implements IUsersService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IMessagesService iMessagesService;

    @Autowired
    EmailUtil emailUtil;

    @Autowired
    OtpUtil otpUtil;

    @Override
    public List<User> getAlList() {
        return this.usersRepository.findAll();
    }

    @Override
    public List<User> getUserIsActive() {
        return this.usersRepository.findByIsActive(true);
    }

    @Override
    public List<User> findIsactiveTeachers() {
        return usersRepository.findByRoleAndIsActive("ROLE_TEACHER", false);
    }

    @Override
    public void toggleActive(Integer userId) {
        Optional<User> optionalUser = usersRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Boolean currentIsActive = user.getIsActive();
            if (currentIsActive != null) {
                user.setIsActive(!currentIsActive);
            } else {
                // Handle case where isActive is null, set it to a default value
                user.setIsActive(true);
            }
            try {
                usersRepository.save(user);
            } catch (Exception e) {
                // Handle database save exception
                e.printStackTrace();
            }
        } else {
            // Handle case where no user found with the given userId
            System.out.println("No user found with userId: " + userId);
        }
    }

    @Override
    public List<User> getTeachers() {
        return usersRepository.findByRole("ROLE_TEACHER");
    }

    @Override
    public List<User> getStudent() {
        return usersRepository.findByRole("ROLE_STUDENT");
    }

    @Override
    public List<User> searchByUsername(String username) {
        return usersRepository.findByUsernameIgnoreCaseContainingAndRole(username, "ROLE_TEACHER");
    }

    @Override
    public List<User> searchByUsernameStudent(String username) {
        return usersRepository.findByUsernameIgnoreCaseContainingAndRole(username, "ROLE_STUDENT");
    }

    @Override
    public boolean updateUser(Integer userId, User updatedUser) {
        try {
            User existingUser = getUsersByID(userId);
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());
            existingUser.setGender(updatedUser.getGender());
            existingUser.setProfilePictureURL(updatedUser.getProfilePictureURL());
            this.usersRepository.save(existingUser);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean create(User users) {
        try {
            this.usersRepository.save(users);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findById(Integer userID) {
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

    @Override
    public Boolean update(User users) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    @Override
    public boolean login(UserLoginDtoRequest userLoginDtoRequest) {
        Optional<User> userOptional = usersRepository.findByUsername(userLoginDtoRequest.getUsername());
        if (userOptional.isEmpty()) {
            return false;
        } else {
            return passwordEncoder.matches(userLoginDtoRequest.getPassword(), userOptional.get().getPasswordHash());
        }
    }

    @Override
    public boolean register(UserRegisterDtoRequest userRegisterDtoRequest) {
        Optional<User> checkUsername = usersRepository.findByUsername(userRegisterDtoRequest.getUsername());
        String checkEmail = usersRepository.findEmailByEmailIgnoreCase(userRegisterDtoRequest.getEmail());
        if (checkUsername.isEmpty() && checkEmail == null) {
            if (userRegisterDtoRequest.getRole().equals("ROLE_STUDENT")) {
                String otp = otpUtil.generateOtp();
                try {
                    emailUtil.sendOtpEmail(userRegisterDtoRequest.getEmail(), otp);
                } catch (MessagingException e) {
                    System.out.println("Unable to send otp please try again");
                    return false;
                }

                userRegisterDtoRequest.setPassword(passwordEncoder.encode(userRegisterDtoRequest.getPassword()));
                User user = usersRepository.save(UserMapper.toUser(userRegisterDtoRequest, otp));
                if (user.getRole().equalsIgnoreCase("ROLE_TEACHER")) {
                    iMessagesService.createNotificationNewAcceptTeacher(user);
                }
                return true;

            } else {
                userRegisterDtoRequest.setPassword(passwordEncoder.encode(userRegisterDtoRequest.getPassword()));
                User user = usersRepository.save(UserMapper.toUser(userRegisterDtoRequest));
                if (user.getRole().equalsIgnoreCase("ROLE_TEACHER")) {
                    iMessagesService.createNotificationNewAcceptTeacher(user);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyAccount(String email, String otp) {
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return false;
        }
        User user = userOptional.get();
        if (user.getOtp().equals(otp)
                && Duration.between(user.getOtpGeneratedTime(), LocalDateTime.now()).getSeconds() < (5 * 60)
                && user.getRole().equals("ROLE_STUDENT")) {
            user.setIsActive(true);
            usersRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean regenerateOtp(String email) {
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return false;
        }
        User user = userOptional.get();
        if (user.getRole().equals("ROLE_STUDENT")) {
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
        if (email == null || email.isEmpty()) {
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
        if (userOptional.isEmpty()) {
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
