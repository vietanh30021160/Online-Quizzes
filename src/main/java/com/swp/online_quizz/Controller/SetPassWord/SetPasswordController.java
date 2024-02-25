package com.swp.online_quizz.Controller.SetPassWord;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SetPasswordController {
    private final IUserService iUserService;
    @GetMapping("/setpassword")
    public String setPasswordRedirect(){
        return "SetNewPassword";
    }
    @PostMapping("/setpassword")
        public String setPassword(Model model,@RequestParam String email, @RequestParam String newPassword, @RequestParam String reNewPassword){
        boolean check = false;
        if(newPassword==null || newPassword.isEmpty()){
            model.addAttribute("pw", "Password can not null");
            check = true;
        }
        if(newPassword.length()<6 && !newPassword.isEmpty()){
            model.addAttribute("pw", "Password must have at least 6 digits");
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
}
