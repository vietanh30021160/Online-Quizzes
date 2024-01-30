package com.swp.online_quizz.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;

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
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/{quizID}")
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model, Authentication auth) {

            User user1 = iUsersService.getUsersByID(2);
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
