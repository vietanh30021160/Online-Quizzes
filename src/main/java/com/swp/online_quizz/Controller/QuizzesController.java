package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;

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

    @GetMapping("/{quizID}")
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model) {
        Users user1 = iUsersService.getUsersByID(8);
        Quizzes quiz = iQuizzesService.getOneQuizz(quizID);
        List<QuizAttempts> listAttempts = iQuizAttemptsService.getAttemptByUserIdAndQuizzId(quiz, user1);
        model.addAttribute("listAttempts", listAttempts);
        model.addAttribute("quiz", quiz);
        return "quizzInfo";
    }

    @GetMapping("/list")
    public List<Quizzes> getAllQuizz() {
        return iQuizzesService.getAllQuizzes();
    }

    @GetMapping("/list/{quizId}")
    public Quizzes getOneQuizz(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId);
    }

    @GetMapping("/question/{quizId}")
    public Set<Questions> getListSubjects(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId).getListQuestions();
    }
}
