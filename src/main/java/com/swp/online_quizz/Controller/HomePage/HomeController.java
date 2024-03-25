package com.swp.online_quizz.Controller.HomePage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IClassEnrollmentService;
import com.swp.online_quizz.Service.IClassQuizzService;
import com.swp.online_quizz.Service.IClassesService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.ISubjectService;
import com.swp.online_quizz.Service.IUsersService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class HomeController {
    @Autowired
    private ISubjectService iSubjectService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private IClassEnrollmentService iClassEnrollmentService;
    @Autowired
    private IClassQuizzService iClassQuizzService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ClassEnrollmentRepository classEnrollmentRepository;
    @Autowired
    private ClassesRepository classesRepository;
    @Autowired
    private IUsersService iUsersService;

    @RequestMapping("")
    public String home(Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String subject,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max,
            @RequestParam(required = false) String className,
            HttpServletRequest request) {
        Optional<User> userOptional = getUserFromSession(request);
        User user = null;


        if (userOptional.isPresent()) {
            user = userOptional.get();
            String role = user.getRole();
            List<Classes> listClassesInUser = iClassesService.getClassesByStudentID(userOptional.get().getUserId());
            Set<Subject> listSubject = iSubjectService.getSubjectsByClasses(listClassesInUser);
            model.addAttribute("listSubject", listSubject);
            if ("ROLE_STUDENT".equals(role)) {
                handleStudentLogic(model, user, keyword, pageNo, min, max, subject, className);
            }
        }
        model.addAttribute("user", user);
        model.addAttribute("className", className);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("keyword", keyword);
        model.addAttribute("subject", subject);
        model.addAttribute("currentPage", pageNo);

        if (userOptional.isPresent()) {
            if ("ROLE_TEACHER".equals(userOptional.get().getRole())) {
                handleStudentLogic(model, user, keyword, pageNo, min, max, subject, className);
                return "HomePageTeacher";
            }
        }
        return "HomePage";
    }

    private Optional<User> getUserFromSession(HttpServletRequest request) {
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            String username = authentication.getName();
            return usersRepository.findByUsername(username);
        }
        return Optional.empty();
    }

    private String handleStudentLogic(Model model, User user, String keyword, Integer pageNo, Integer min, Integer max,
            String subject, String className) {

        Integer userId = user.getUserId();
        List<Integer> classIds = iClassEnrollmentService.getClassIdsByStudentId(userId);
        List<Integer> quizIds = iClassQuizzService.getQuizIdsByClassIds(classIds);
        Page<Quiz> filteredQuiz = iQuizzesService.CombineQuizzes(keyword, pageNo, min, max, subject, quizIds, className);
        List<Classes> listClassesInUser = iClassesService.getClassesByStudentID(userId);
        int totalPage = filteredQuiz.getTotalPages();

        model.addAttribute("listClasses", listClassesInUser);
        model.addAttribute("listQuiz", filteredQuiz);
        model.addAttribute("totalPage", totalPage);

        return "HomePage";
    }
    @PostMapping("/join")
    public String joinClass(@RequestParam("classCode") String classCode, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        Optional<User> userOptional = getUserFromSession(request);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        if (classCode != null) {

            if (iClassEnrollmentService.existsByStudentIdAndClassCode(userOptional.get().getUserId(), classCode)) {
                redirectAttributes.addFlashAttribute("mess", "You have already taken this class or the classcode is wrong");
            } else {
                iClassesService.joinClass(classCode, userOptional.get().getUserId());
                redirectAttributes.addFlashAttribute("classCode", classCode);
                redirectAttributes.addFlashAttribute("mess", "Join class successfully!");
            }
        }

        // Redirect to home page
        return "redirect:/";
    }

    @GetMapping("/information")
    public String informationPage(Model model, HttpServletRequest request) {
        Optional<User> userOptional = getUserFromSession(request);
        if (userOptional.isEmpty()) {
            // Nếu không có user thì làm gì đấy
            return "redirect:/login";
        }
        // nếu có thì lấy ra user
        User user = userOptional.get();
        model.addAttribute("user", user);
        return "Information.html";
    }

    @GetMapping("/updateInformation")
    public String updateInformation(Model model, HttpServletRequest request) {
        Optional<User> userOptional = getUserFromSession(request);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOptional.get();
        model.addAttribute("user", user);
        return "UpdateInformation";
    }

    // Phương thức xử lý yêu cầu POST từ trang cập nhật thông tin người dùng
    @PostMapping("/updateInformation")
    public String updateInformation(HttpServletRequest request, @ModelAttribute User updatedUser) {
        Optional<User> userOptional = getUserFromSession(request);
        // Thực hiện cập nhật thông tin người dùng
        if (userOptional.isEmpty()) {
            // Nếu không có người dùng, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }
        iUsersService.updateUser(userOptional.get().getUserId(), updatedUser);
        return "redirect:/information";
    }
}
