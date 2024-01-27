package com.swp.online_quizz.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Users;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IQuesstionAttemptsService;
import com.swp.online_quizz.Service.IQuestionsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizProgressService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/attempt")
public class QuizAttemptController {
    @Autowired
    private IAnswerService iAnswerService;
    @Autowired
    private IUsersService iUsersService;
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

    @GetMapping("/")
    public String test() {
        return "doQuizz";
    }

    @GetMapping("/question")
    public List<Questions> getAllQuestions() {
        return iQuestionsService.getAllQuestions();
    }

    @GetMapping("/testApi/{quizId}")
    public List<QuizAttempts> testApi(@PathVariable Integer quizId, Model model) {
        Users user = iUsersService.getUsersByID(8);
        Quizzes quizz = iQuizzesService.getOneQuizz(quizId);
        Timestamp startTime = Timestamp.valueOf("2024-01-25 02:18:35.316");
        return iQuizAttemptsService.findByQuizIdAndUserIdAndStartTime(quizz, user, startTime);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/attemptQuiz/{quizId}")
    public RedirectView attemptQuizz(@PathVariable Integer quizId, Model model) {
        Users user = iUsersService.getUsersByID(8);
        if (user != null) {
            Quizzes quizz = iQuizzesService.getOneQuizz(quizId);
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            long endTimeMillis = startTime.getTime() + (5 * 60 * 1000);
            Timestamp endTime = new Timestamp(endTimeMillis);
            long startTimeSearchMillis = startTime.getTime() - (10);
            Timestamp startTimeSearch = new Timestamp(startTimeSearchMillis);
            List<Questions> listQuestion = getRandomQuestionsFromSet(quizId, 3); // số lượng câu hỏi trong 1 bài quiz
                                                                                 // được tạo
            QuizAttempts newAttemp = new QuizAttempts(0, user, quizz, startTime, endTime,
                    0, false, listQuestion.get(0).getQuestionId(), null, null, null);
            iQuizAttemptsService.createQuizzAttempt(newAttemp);
            List<QuizProgress> listQProg = new ArrayList<>();
            QuizAttempts attemp = iQuizAttemptsService.findByQuizIdAndUserIdAndStartTime(quizz,
                    user, startTimeSearch).get(0);
            for (int i = 0; i < listQuestion.size(); i++) {
                iQuesstionAttemptsService
                        .createQuesstionAttempts(new QuestionAttempts(attemp, listQuestion.get(i), "", false,
                                (i + 1), false));
                QuizProgress qp = new QuizProgress(attemp, listQuestion.get(i), false, (i + 1), "");
                listQProg.add(qp);
                iQuizProgressService.createQuizProgress(qp);
            }
            return new RedirectView("/attempt/attemptQuiz/" + quizId + "/" + attemp.getAttemptId() + "/1");
        } else {
            return new RedirectView("/attempt/login");
        }
    }

    @GetMapping("/attemptQuiz/{quizId}/{attemptID}/{page}")
    public String attemptQuizzQuestionNumber(@PathVariable Integer quizId, @PathVariable Integer attemptID,
            @PathVariable Integer page, HttpSession session, Model model) {
        Users user = iUsersService.getUsersByID(8);
        if (user != null) {
            QuizAttempts attemp = iQuizAttemptsService.getQuizAttempts(attemptID);
            List<QuizProgress> listQProg = new ArrayList<>(attemp.getListQuizzProgress());
            for (QuizProgress quizProgress : listQProg) {
                if (quizProgress.getQuestionOrder() == page) {
                    attemp.setCurrentQuestionId(quizProgress.getQuestion().getQuestionId());
                    iQuizAttemptsService.updateAttempts(attemptID, attemp);
                }
            }
            page -= 1;
            Questions question = listQProg.get((page)).getQuestion();
            QuizProgress quizProgress = listQProg.get((page));
            String answerString = quizProgress.getAnswer();
            int answerProg = 0;
            if (answerString != null && !answerString.isEmpty()) {
                answerProg = Integer.parseInt(answerString);
            } else {
                answerProg = 0;
            }
            Quizzes quiz = attemp.getQuiz();
            page += 1;
            model.addAttribute("answerProg", answerProg);
            model.addAttribute("page", page);
            model.addAttribute("attemp", attemp);
            model.addAttribute("quiz", quiz);
            model.addAttribute("question", question);
            model.addAttribute("listQProg", listQProg);
            model.addAttribute("QuizProgress", new QuizProgress());
            return "doQuizz";
        } else {
            return "Login";
        }
    }

    @PostMapping("/progress")
    public RedirectView progress(@ModelAttribute("user") QuizProgress progress,
            @RequestParam(name = "previous", required = false) String previous,
            @RequestParam(name = "next", required = false) String next,
            @RequestParam(name = "attempID", required = false) String attempIDString,
            @RequestParam(name = "page", required = false) String pageString,
            @RequestParam(name = "questionID", required = false) String questionIDString) {
        String answerProgress = progress.getAnswer();
        Integer questionID = Integer.parseInt(questionIDString);
        Integer page = Integer.parseInt(pageString);
        Questions questionProgress = iQuestionsService.getQuestions(questionID);
        Integer attempID = Integer.parseInt(attempIDString);
        QuizAttempts attemp = iQuizAttemptsService.getQuizAttempts(attempID);
        for (QuestionAttempts questionAttempts : attemp.getListQuestionAttempts()) {
            if (questionAttempts.getQuestion().getQuestionId() == questionProgress.getQuestionId()) {
                if (answerProgress != null) {
                    if (iAnswerService.getAnswers(Integer.parseInt(answerProgress)).getIsCorrect()) {
                        questionAttempts.setIsCorrect(true);
                    }
                }
                questionAttempts.setAnswer(answerProgress);
                questionAttempts.setIsAnswered(true);
                iQuesstionAttemptsService.updateQuesstionAttempts(questionAttempts.getQuestionAttemptID(),
                        questionAttempts);
            }
        }
        for (QuizProgress quizProgress : attemp.getListQuizzProgress()) {
            if (quizProgress.getQuestion().getQuestionId() == questionProgress.getQuestionId()) {
                quizProgress.setAnswer(answerProgress);
                if (answerProgress != null) {
                    quizProgress.setIsAnswered(true);
                }
                iQuizProgressService.updateQuizProgress(quizProgress.getProgressId(),
                        quizProgress);
            }
        }
        if (previous != null) {
            if (page > 1) {
                page -= 1;
            }
            return new RedirectView(
                    "/attempt/attemptQuiz/" + attemp.getQuiz().getQuizId() + "/" + attemp.getAttemptId() + "/"
                            + (page));
        }
        if (next != null) {
            if (page < attemp.getListQuizzProgress().size()) {
                page += 1;
            }
            return new RedirectView(
                    "/attempt/attemptQuiz/" + attemp.getQuiz().getQuizId() + "/" + attemp.getAttemptId() + "/"
                            + (page));
        }
        return new RedirectView(
                "/attempt/attemptQuiz/" + attemp.getQuiz().getQuizId() + "/" + attemp.getAttemptId() + "/"
                        + page);
    }

    @GetMapping("/attemptQuiz/{quizId}/{attemptID}/finish")
    public RedirectView finishQuizAttempt(@PathVariable Integer quizId, @PathVariable Integer attemptID,
            HttpSession session,
            Model model) {
        QuizAttempts attempt = iQuizAttemptsService.getQuizAttempts(attemptID);
        attempt.setIsCompleted(true);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        attempt.setEndTime(endTime);
        int count = 0;
        for (QuestionAttempts questionAttempts : attempt.getListQuestionAttempts()) {
            if (questionAttempts.getIsCorrect() == true) {
                count++;
            }
        }
        attempt.setMarks((Integer) (count / attempt.getListQuestionAttempts().size()));
        iQuizAttemptsService.updateAttempts(attemptID, attempt);
        return new RedirectView("/quizzes/{quizId}");
    }

    public List<Questions> getRandomQuestionsFromSet(@PathVariable Integer quizId, @PathVariable Integer n) {
        Set<Questions> questionsSet = iQuizzesService.getOneQuizz(quizId).getListQuestions();
        List<Questions> questionsList = new ArrayList<>(questionsSet);
        Collections.shuffle(questionsList);
        List<Questions> randomQuestions = questionsList.subList(0, Math.min(n, questionsList.size()));
        return randomQuestions;
    }
}
