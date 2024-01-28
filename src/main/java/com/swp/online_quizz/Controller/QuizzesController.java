package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;
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
        User user1 = iUsersService.getUsersByID(8);
        Quiz quiz = iQuizzesService.getOneQuizz(quizID);
        List<QuizAttempt> listAttempts = iQuizAttemptsService.getAttemptByUserIdAndQuizzId(quiz, user1);
        model.addAttribute("listAttempts", listAttempts);
        model.addAttribute("quiz", quiz);
        return "quizzInfo";
    }

    @GetMapping("/list")
    public List<Quiz> getAllQuizz() {
        return iQuizzesService.getAllQuizzes();
    }

    @GetMapping("/list/{quizId}")
    public Quiz getOneQuizz(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId);
    }

    @GetMapping("/question/{quizId}")
    public Set<Question> getListSubjects(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId).getListQuestions();
    }
}
