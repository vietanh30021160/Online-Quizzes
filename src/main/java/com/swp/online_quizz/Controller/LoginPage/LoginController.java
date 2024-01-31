package com.swp.online_quizz.Controller.LoginPage;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Service.CustomUserDetails;
import com.swp.online_quizz.Service.CustomUserDetailsServices;
import com.swp.online_quizz.Service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService iUserService;
    private final UserDetailsService userDetailsService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        return "Login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
        return "Register";
    }
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute UserRegisterDtoRequest userRegisterDtoRequest){
        String ms = "";

        if(userRegisterDtoRequest.getUsername()==null){
            model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
            model.addAttribute("ms", "Username can not be null!");
            return "Register";
        }
        if(userRegisterDtoRequest.getEmail()==null){
            model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
            model.addAttribute("ms", "Username can not be null!");
            return "Register";
        }
        if(userRegisterDtoRequest.getPassword()==null){
            model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
            model.addAttribute("ms", "Password can not be null!");
            return "Register";
        }
        if(userRegisterDtoRequest.getRole()==null){
            model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
            model.addAttribute("ms", "Role can not be null!");
            return "Register";
        }

        boolean isValid = iUserService.register(userRegisterDtoRequest);
        if(isValid){
            return "redirect:/login";
        }
        model.addAttribute("ms", "Register unsuccessfully!");
        model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
        return "Register";
    }

    @GetMapping("/forgotpassword")
    public String forgotPage(){
        return "ForgotPassword";
    }

    @GetMapping("/logout")
    public String logoutSuccess(){
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String adminPage(){
        return "Admin";
    }
    @GetMapping("/check")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        System.out.println("NULL " + userDetails.getUsername());
        return "redirect:/login";
    }
}
