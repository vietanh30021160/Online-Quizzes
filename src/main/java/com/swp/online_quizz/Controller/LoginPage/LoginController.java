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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService iUserService;
    private final UserDetailsService userDetailsService;

//    @GetMapping("/login")
//    public String loginPage(Model model) {
//        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
//        return "Login";
//    }
//
//    @PostMapping("/login")
//    public String loginSucess(HttpServletRequest request){
//
//
//        if(request.getAttribute("authentication")!=null) {
//            Authentication authentication = (Authentication)request.getAttribute("authentication");
//            String username = authentication.getName();
//            Object principal = authentication.getPrincipal();
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
////        System.out.println("NULL " + userDetails.getUsername());
//            System.out.println("Name " + username);
//            System.out.println("Principal " + principal);
//            System.out.println("Authorities " + authorities);
//        }
//        return "redirect:/";
//    }

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
        if(!(ms.length()==0||ms==null)){
            model.addAttribute("ms", "Register unsuccessfully!");
            model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
            return "Register";
        }

        return "redirect:/login";
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



    @GetMapping("/homePageTeacher")
    public String teacherPage(){
        return "HomePageTeacher";
    }

    @GetMapping("/login")
public String loginPage(Model model) {
    model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
    return "Login";
}

    @PostMapping("/login")
    public String loginSucess(HttpServletRequest request){


        if(SecurityContextHolder.getContext()!=null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Object principal = authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        System.out.println("NULL " + userDetails.getUsername());
            System.out.println("Name " + username);
            System.out.println("Principal " + principal);
            System.out.println("Authorities " + authorities);
        }
        return "redirect:/";
    }
}
