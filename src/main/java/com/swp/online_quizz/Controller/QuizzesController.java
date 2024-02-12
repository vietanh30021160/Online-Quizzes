package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/quizzes")
public class QuizzesController {
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private IQuizAttemptsService iQuizAttemptsService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/{quizID}")
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model, Authentication auth,
            HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            // Nếu không có user thì làm gì đấy
            return "redirect:/login";
        }
        // nếu có thì lấy ra user
        User user1 = userOptional.get();

        Quiz quiz = iQuizzesService.getOneQuizz(quizID);
        List<QuizAttempt> listAttempts = iQuizAttemptsService.getAttemptByUserIdAndQuizzId(quiz, user1);
        Integer highestMark = 0;
        for (QuizAttempt quizAttempt : listAttempts) {
            if (quizAttempt.getMarks() > highestMark) {
                highestMark = quizAttempt.getMarks();
            }
        }
        model.addAttribute("listAttempts", listAttempts);
        model.addAttribute("quiz", quiz);
        model.addAttribute("highestMark", highestMark);
        return "quizzInfo";

    }

}
