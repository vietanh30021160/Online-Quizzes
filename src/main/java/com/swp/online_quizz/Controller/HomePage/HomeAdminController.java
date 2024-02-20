package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class HomeAdminController {
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private UsersRepository usersRepository;
    @GetMapping("")
    public String getDashboard(Model model, HttpServletRequest request) {
        String role ="ROLE_ADMIN";
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty() || !role.equals(userOptional.get().getRole())) {
            //Nếu không có user thì làm gì đấy
            return "redirect:/login";
        }
        //nếu có thì lấy ra user
        if( role.equals(userOptional.get().getRole())){
            String userName = userOptional.get().getUsername();
            int numberOfUsers = iUsersService.getUserIsActive().size();
            int numberOfQuizzes = iQuizzesService.getAll().size();
            int numberOfAccept = iUsersService.findIsactiveTeachers().size();
            model.addAttribute("numberOfUsers", numberOfUsers);
            model.addAttribute("numberOfQuizzes", numberOfQuizzes);
            model.addAttribute("userName", userName);
            model.addAttribute("numberOfAccept",numberOfAccept);
        }
        return "Admin";
    }
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
    @GetMapping("/student/search")
    public String searchUsersStudent(@RequestParam("username") String username, Model model) {
        List<User> foundStudents = iUsersService.searchByUsernameStudent(username);
        model.addAttribute("students", foundStudents);
        return "StudentsList";
    }

    @PostMapping("/profile")
    public String updateUserProfile(@ModelAttribute("user") User updatedUser, HttpServletRequest request) {
        String username = "";
        if(request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if(userOptional.isEmpty()) {
            // Nếu không có người dùng, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }
        User existingUser = userOptional.get();
        // Gọi phương thức trong service để cập nhật thông tin người dùng
        iUsersService.updateUser(existingUser.getUserId(), updatedUser);
        return "redirect:/admin/profile";
    }

    @GetMapping("/profile")
    public String showUpdateForm(Model model, HttpServletRequest request) {
        String username = "";
        if(request.getSession().getAttribute("authentication")!=null){
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username= authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            //Neu khong co user thi sao?
            return "redirect:/login";
        }
        //nếu có thì lấy ra user
        User user = userOptional.get();
        model.addAttribute("user", user);
        return "UpdateProfile";
    }

}
