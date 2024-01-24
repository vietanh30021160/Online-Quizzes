package com.swp.online_quizz.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Service.IQuesstionAttemptsService;
import com.swp.online_quizz.Service.IQuestionsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizProgressService;
import com.swp.online_quizz.Service.IQuizzesService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/attempt")
public class QuizAttemptController {
    @Autowired
    private IQuesstionAttemptsService iQuesstionAttemptsService;
    @Autowired
    private IQuizProgressService iQuizProgressService;
    @Autowired
    private IQuestionsService iQuestionsService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private IQuizAttemptsService iQuizAttemptsService;

    @GetMapping("")
    public String test(Model model) {
        return "quizzInfo";
    }

    @GetMapping("/question")
    public List<Questions> getAllQuestions() {
        return iQuestionsService.getAllQuestions();
    }

    @GetMapping("/{attemptID}")
    public String getQuizAttempt(@PathVariable Integer attemptID, Model model) {
        QuizAttempts attempt = iQuizAttemptsService.getQuizAttempts(attemptID);
        model.addAttribute(null, attempt);

        return "quizzInfo";
    }

    @GetMapping("/attemptQuiz/{quizId}")
    public String attemptQuizz(@RequestParam Integer quizId, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            Quizzes quizz = iQuizzesService.getOneQuizz(quizId);
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            long endTimeMillis = startTime.getTime() + (5 * 60 * 1000);
            Timestamp endTime = new Timestamp(endTimeMillis);
            List<Questions> listQuestion = getRandomQuestionsFromSet(quizId, 5);
            QuizAttempts newAttemp = new QuizAttempts(0, user, quizz, startTime, endTime,
                    -1, false, 1, null, null, null);
            iQuizAttemptsService.createQuizzAttempt(newAttemp);
            QuizAttempts attemp = iQuizAttemptsService.findByQuizIdAndUserIdAndStartTime(quizz,
                    user, startTime).get(0);
            for (int i = 0; i < listQuestion.size(); i++) {
                iQuesstionAttemptsService
                        .createQuesstionAttempts(new QuestionAttempts(i, attemp, listQuestion.get(i), null, false,
                                i + 1, false));
                iQuizProgressService
                        .createQuizProgress(new QuizProgress(0, attemp, listQuestion.get(i), false, i + 1, null));
            }

            model.addAttribute("listQuestion", listQuestion);
            model.addAttribute("attemp", attemp);
            return "doQuizz";
        } else {
            return "Login";
        }
    }

    @GetMapping("/attemptQuiz/{attemptID}/{question}")
    public String attemptQuizzQuestionNumber(@PathVariable Integer attemptID,
            @RequestParam Integer question, HttpSession session, Model model) {
        Users user = (Users) session.getAttribute("user");
        if (user != null) {
            QuizAttempts attemp = iQuizAttemptsService.getQuizAttempts(attemptID);

            model.addAttribute("attemp", attemp);
            return "Complete";
        } else {
            return "Login";
        }
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
