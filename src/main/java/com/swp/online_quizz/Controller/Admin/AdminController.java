package com.swp.online_quizz.Controller.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.PublicKey;

@Controller
public class AdminController {
    @RequestMapping("/admin")
    public String admin(){
        return "Html/Admin/Index.html";
    }
}
