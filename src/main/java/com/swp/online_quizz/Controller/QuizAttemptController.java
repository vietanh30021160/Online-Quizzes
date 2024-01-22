package com.swp.online_quizz.Controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Service.IQuestionsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizzesService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/attempt")
public class QuizAttemptController {
    @Autowired
    private IQuestionsService iQuestionsService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private IQuizAttemptsService iQuizAttemptsService;

    @GetMapping("")
    public String test() {
        return "quizzInfo";
    }

    @GetMapping("/question")
    public List<Questions> getAllQuestions() {
        return iQuestionsService.getAllQuestions();
    }

    @GetMapping("/{attemptID}")
    public QuizAttempts getQuizAttempt(@PathVariable Integer attemptID) {
        return iQuizAttemptsService.getQuizAttempts(attemptID);
    }

    @GetMapping("/attemptQuiz/{quizId}")
    public String getMethodName(@RequestParam Integer quizId, HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            Quizzes quizz = iQuizzesService.getOneQuizz(quizId);
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            QuizAttempts attemp = new QuizAttempts(0, user, quizz, startTime, null,
                    0, null, 1, null, null, null);
            if (iQuizAttemptsService.createQuizzAttempt(attemp)) {

            }
        }
        return "Complete";
    }

}
