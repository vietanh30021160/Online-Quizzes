package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IClassesService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.ISubjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller

public class HomeController {
    @Autowired
    private ISubjectService iSubjectService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping("")
    public String Home(Model model,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String subject,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) Integer min,
                       @RequestParam(required = false) Integer max,
                       @RequestParam(required = false) String classCode,
                       HttpServletRequest request) {
        List<Subject> listSubject = iSubjectService.getAll();
        Page<Quiz> listQuiz = iQuizzesService.searchAndFilterAndSubject(keyword, pageNo, min, max, subject);
        String role = "ROLE_STUDENT";
        // Thêm role Teacher
        String role_Teacher = "ROLE_TEACHER";
        String username = "";
        int totalPage = listQuiz.getTotalPages(); // Lấy số trang từ listQuiz
        if (classCode != null) {
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
            if (role.equals(userOptional.get().getRole())) {
                Integer user1 = userOptional.get().getUserId();
                iClassesService.joinClass(classCode, user1);
            }
            model.addAttribute("classCode", classCode);
        }
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("keyword", keyword);
        model.addAttribute("subject", subject);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listSubject", listSubject);
        model.addAttribute("listQuiz", listQuiz);
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (role_Teacher.equals(userOptional.get().getRole())) {
            return "HomePageTeacher";
        }
        return "HomePage";
    }


}
