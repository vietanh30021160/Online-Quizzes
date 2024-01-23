package com.swp.online_quizz.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@Controller
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
        return "quizzInfo.html";
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
    public String attemptQuizz(@RequestParam Integer quizId, HttpSession session) {
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

    public List<Questions> getRandomQuestionsFromSet(@PathVariable Integer quizId, @PathVariable Integer n) {
        Set<Questions> questionsSet = iQuizzesService.getOneQuizz(quizId).getListQuestions();
        List<Questions> questionsList = new ArrayList<>(questionsSet);
        Collections.shuffle(questionsList);
        // Lấy n câu hỏi từ đầu danh sách (hoặc ít hơn n nếu danh sách không đủ n phần
        // tử)
        List<Questions> randomQuestions = questionsList.subList(0, Math.min(n, questionsList.size()));
        return randomQuestions;
    }
}
