package com.swp.online_quizz.Controller.ForgotPasswordPage;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UserService userService;
    private final UsersRepository usersRepository;
    @GetMapping("/forgotpassword")
    public String forgotPage(){
        return "ForgotPassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword(Model model, @RequestParam(value = "email") String email){
        Optional<User> userOptional = usersRepository.findByEmailIgnoreCase(email);
        if(userOptional.isEmpty()){
            model.addAttribute("error","Email has not used yet! Do you want to ");
            return "ForgotPassword";
        }
        User user = userOptional.get();
        if(user.getIsActive()) {
            if (!userService.forgotPassword(email)) {
                model.addAttribute("error", "This email has not used yet! Do you want to ");
                return "ForgotPassword";
            }
            model.addAttribute("ms", "Please check your email to set new password to your account");
        }else{
            model.addAttribute("ms", "Please activate your account first");

        }
        return "ForgotPassword";
    }
}
