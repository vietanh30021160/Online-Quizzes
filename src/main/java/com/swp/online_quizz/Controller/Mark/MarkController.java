package com.swp.online_quizz.Controller.Mark;

import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/class")
public class MarkController {
    @Autowired
    private UsersService usersService;
@GetMapping("/mark")
    public String index(Model model){
    List<Users> list = this.usersService.getAlList();
    model.addAttribute("ListStuden",list);
        return "class/mark";
    }
}
