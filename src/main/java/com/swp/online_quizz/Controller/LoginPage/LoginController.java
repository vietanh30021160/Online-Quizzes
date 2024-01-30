package com.swp.online_quizz.Controller.LoginPage;

import com.swp.online_quizz.Dto.UserLoginDtoRequest;
import com.swp.online_quizz.Dto.UserRegisterDtoRequest;
import com.swp.online_quizz.Service.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class LoginController {

    private final IUserService iUserService;


    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        return "Login";
    }

    @PostMapping("/login")
    public String loginPageSuccess(Model model, @ModelAttribute UserLoginDtoRequest loginDtoRequest, HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(name = "rememberMe") String rememberMe) {

        Cookie[] cookies = request.getCookies();
        Cookie remValue = null;
        Cookie username = null;
        Cookie password = null;
        if(rememberMe!=null){

            // Lấy danh sách cookie từ request

            // Kiểm tra xem danh sách cookie có tồn tại không
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "rem":
                        remValue = cookie;
                        break;
                    case "username":
                        username = cookie;
                        break;
                    case "password":
                        password = cookie;
                        break;
                }
            }
            if(remValue==null){
                username = new Cookie("username", loginDtoRequest.getUsername());
                password = new Cookie("password", loginDtoRequest.getPassword());
                remValue = new Cookie("rem", rememberMe);
            }
            final int MONTH = 24*60*60*30;
            username.setMaxAge(MONTH);
            password.setMaxAge(MONTH);
            remValue.setMaxAge(MONTH);
        }else{
            username.setMaxAge(0);
            password.setMaxAge(0);
            remValue.setMaxAge(0);
        }
        response.addCookie(username);
        response.addCookie(password);
        response.addCookie(remValue);


        model.addAttribute("userLoginDtoRequest", UserLoginDtoRequest.builder().build());
        model.addAttribute("username", username.getValue());
        model.addAttribute("password", password.getValue());
        model.addAttribute("remValue", remValue.getValue());

        return "Login";
    }

//    @PostMapping("/login")
//    public String login(Model model,@ModelAttribute UserLoginDtoRequest userLoginDtoRequest){
//        boolean isTrue = iUserService.login(userLoginDtoRequest);
//        System.out.println(userLoginDtoRequest.getUsername());
//        if(isTrue){
//            return "redirect:/";
//        }
//        model.addAttribute("success", "");
//        model.addAttribute("userLoginDtoRequest",UserLoginDtoRequest.builder().build());
//        return "Login";
//    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("userRegisterDtoRequest", UserRegisterDtoRequest.builder().build());
        return "Register";
    }
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute UserRegisterDtoRequest userRegisterDtoRequest){
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

    @GetMapping("/admin")
    public String adminPage(){
        return "Admin";
    }
}
