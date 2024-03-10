package com.swp.online_quizz.Controller.SetPassWord;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class SetPasswordController {
    private final IUserService iUserService;
    private String pwRegex ="((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!]).{6,20})";

    @GetMapping("/setpassword")
    public String setPasswordRedirect(){
        return "SetNewPassword";
    }
    @PostMapping("/setpassword")
        public String setPassword(Model model,@RequestParam String email, @RequestParam String newPassword, @RequestParam String reNewPassword){
        boolean check = false;
        Pattern pattern = Pattern.compile(pwRegex);
        Matcher matcher = pattern.matcher(newPassword);
        check = isCheck(model, newPassword, reNewPassword, check, matcher);
        if(check){
            model.addAttribute("ms", "Set new password unsuccessfully!");
            model.addAttribute("email", email);
            return "SetNewPassword";
        }

        if(!iUserService.setPassword(email,newPassword)){
            model.addAttribute("email", email);

            return "SetNewPassword";
        }
        model.addAttribute("ms","Set new password successfully");
        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        return "Login";
    }

    public static boolean isCheck(Model model, @RequestParam String newPassword, @RequestParam String reNewPassword, boolean check, Matcher matcher) {
        if(newPassword==null || newPassword.isEmpty()){
            model.addAttribute("pw", "Password can not null");
            check = true;
        }else if(!matcher.matches()){
            model.addAttribute("pw", "At least a digit, a lowercase word, a uppercase word and a special character");
            check = true;
        }
        if(!reNewPassword.equals(newPassword)){
           model.addAttribute("rpw","Re-enter password does not match");
           check = true;
       }
        if(reNewPassword.isEmpty()){
            model.addAttribute("rpw","Re-enter password can not null");
            check = true;
        }
        return check;
    }
}
