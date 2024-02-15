package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private IClassEnrollmentService iClassEnrollmentService;
    @Autowired
    private IClassQuizzService iClassQuizzService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ClassEnrollmentRepository classEnrollmentRepository;


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
        int totalPage;

        // Lấy danh sách bài trắc nghiệm dựa trên các tham số tìm kiếm và lọc
        Page<Quiz> listQuiz = iQuizzesService.searchAndFilterAndSubject(keyword, pageNo, min, max, subject);

        // Tính toán số trang và tổng số bài trắc nghiệm
        totalPage = listQuiz.getTotalPages();

        String role = "ROLE_STUDENT";
        String username = "";

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();

            // Nếu người dùng đã đăng nhập và có vai trò là sinh viên, thì lọc danh sách bài trắc nghiệm dựa trên quizIds của sinh viên
            Optional<User> userOptional = usersRepository.findByUsername(username);
            if (role.equals(userOptional.get().getRole())) {
                Integer user1 = userOptional.get().getUserId();
                List<Integer> classIds = iClassEnrollmentService.getClassIdsByStudentId(user1);
                List<Integer> quizIds = iClassQuizzService.getQuizIdsByClassIds(classIds);
                Page<Quiz> filteredQuiz = iQuizzesService.searchAndFilterAndSubjectAndQuizIds(keyword, pageNo, min, max, subject, quizIds);
                // Lấy danh sách bài trắc nghiệm đã lọc và cập nhật lại số trang
                listQuiz = filteredQuiz;
                totalPage = filteredQuiz.getTotalPages();
            }
        }

        // Nếu có mã lớp học được truyền vào, thì thực hiện tham gia vào lớp học
        if (classCode != null) {
            if (username.isEmpty()) {
                // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
                return "redirect:/login";
            } else {
                Optional<User> userOptional = usersRepository.findByUsername(username);
                if (role.equals(userOptional.get().getRole())) {
                    Integer user2 = userOptional.get().getUserId();
                    // Kiểm tra xem classCode đã tồn tại hay không
                    if (iClassEnrollmentService.existsByStudentIdAndClassCode(user2,classCode)) {
                        // Nếu tồn tại, hiển thị thông báo lỗi và không thực hiện thêm vào lớp
                        model.addAttribute("mess", "You have already taken this class or the classcode is wrong");
                    } else {
                        // Nếu không tồn tại, thêm vào lớp và hiển thị classCode
                        iClassesService.joinClass(classCode, user2);
                        model.addAttribute("classCode", classCode);
                        model.addAttribute("mess", "Join class successfully!");
                    }
                }
            }
        }

        // Gửi các thông tin cần thiết tới trang chủ
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("keyword", keyword);
        model.addAttribute("subject", subject);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listSubject", listSubject);
        model.addAttribute("listQuiz", listQuiz);

        return "HomePage";
    }



}
