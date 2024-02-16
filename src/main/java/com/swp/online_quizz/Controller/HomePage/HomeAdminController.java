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
    public String toggleActive(@RequestParam("userId") Integer userId,
                               @RequestParam("redirectUrl") String redirectUrl) {
        iUsersService.toggleActive(userId);
        return "redirect:" + redirectUrl;
    }
    @GetMapping("/teachers")
    public String getTeachers(Model model) {
        List<User> teachers = iUsersService.getTeachers();
        model.addAttribute("teachers", teachers);
        return "TeachersList";
    }
    @GetMapping("/student")
    public String getStudent(Model model) {
        List<User> students = iUsersService.getStudent();
        model.addAttribute("students", students);
        return "StudentsList";
    }
    @GetMapping("/teacher/search")
    public String searchUsers(@RequestParam("username") String username, Model model) {
        List<User> foundUsers = iUsersService.searchByUsername(username);
        model.addAttribute("teachers", foundUsers);
        return "TeachersList";
    }
}
