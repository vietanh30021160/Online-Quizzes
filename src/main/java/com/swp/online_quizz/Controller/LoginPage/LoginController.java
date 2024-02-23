package com.swp.online_quizz.Controller.LoginPage;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Service.CustomUserDetails;
import com.swp.online_quizz.Service.CustomUserDetailsServices;
import com.swp.online_quizz.Service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService iUserService;
    private final UserDetailsService userDetailsService;
    @GetMapping("/register")
    public String registerPage(Model model, HttpSession session){
        String email = (String) session.getAttribute("email");
        String firstnameEmail = (String) session.getAttribute("firstnameEmail");
        String lastnameEmail = (String) session.getAttribute("lastnameEmail");
        if(email!=null){
            model.addAttribute("name",email);
            model.addAttribute("lastnameEmail",lastnameEmail);
            model.addAttribute("firstnameEmail",firstnameEmail);
        }
        model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
        return "Register";
    }
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute UserRegisterDtoRequest userRegisterDtoRequest,@RequestParam(value = "name1", required = false) String name1
            ,@RequestParam(value = "repassword", required = false) String repassword
            ,@RequestParam(value = "firstnameEmail1", required = false) String firstnameEmail1
            ,@RequestParam(value = "lastnameEmail1", required = false) String lastnameEmail1){
        if (name1 != null && !name1.isEmpty()) {
            model.addAttribute("name",name1);
        }
        if (firstnameEmail1 != null && !firstnameEmail1.isEmpty()) {
            model.addAttribute("firstnameEmail",firstnameEmail1);
        }
        if (lastnameEmail1 != null && !lastnameEmail1.isEmpty()) {
            model.addAttribute("lastnameEmail",lastnameEmail1);
        }
        String ms = "";
        boolean check = false;
        if(userRegisterDtoRequest.getUsername()==null|| userRegisterDtoRequest.getUsername().isEmpty()){
            model.addAttribute("us", "Username can not null");
            check = true;
        }
        if(userRegisterDtoRequest.getEmail()==null || userRegisterDtoRequest.getEmail().isEmpty()){
            model.addAttribute("em", "Email can not null");
            check = true;
        }
        if(userRegisterDtoRequest.getPassword()==null || userRegisterDtoRequest.getPassword().isEmpty()){
            model.addAttribute("pw", "Password can not null");
            check = true;
        }
        if(userRegisterDtoRequest.getPassword().length()<6 && !userRegisterDtoRequest.getPassword().isEmpty()){
            model.addAttribute("pw", "Password must have at least 6 digits");
            check = true;
        }
        if(!repassword.equals(userRegisterDtoRequest.getPassword())){
            model.addAttribute("rpw","Re-enter password does not match");
            check = true;
        }
        if(repassword.isEmpty()){
            model.addAttribute("rpw","Re-enter password can not null");
            check = true;
        }
        if(userRegisterDtoRequest.getRole()==null){
            model.addAttribute("rl", "Role can not null");
            check = true;
        }
        if(check){
            model.addAttribute("ms", "Register unsuccessfully!");
            return "Register";
        }

        boolean isValid = iUserService.register(userRegisterDtoRequest);
        if(isValid){
            ms="Register successfully!";
            model.addAttribute("ms", ms);
            model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        }
        return "Login";
    }

    @GetMapping("/forgotpassword")
    public String forgotPage(){
        return "ForgotPassword";
    }

    @GetMapping("/logout")
    public String logoutSuccess(HttpServletRequest request){
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "Admin";
    }

    @GetMapping("/homePageTeacher")
    public String teacherPage(){
        return "HomePageTeacher";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        return "Login";
    }
}
