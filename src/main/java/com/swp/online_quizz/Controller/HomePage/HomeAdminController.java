package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class HomeAdminController {
    @Autowired
    private IUsersService iUsersService;

    @GetMapping("/isActive-teachers")
    public String getIsActiveTeachers(Model model) {
        List<User> isActiveTeachers = iUsersService.findIsactiveTeachers();
        model.addAttribute("userList", isActiveTeachers);
        return "AcceptTeacher";
    }
    @PostMapping("/toggle-active")
    public String toggleActive(@RequestParam("userId") Integer userId) {
        iUsersService.toggleActive(userId);
        return "redirect:/admin/isActive-teachers";
    }
}
