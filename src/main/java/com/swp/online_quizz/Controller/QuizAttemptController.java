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

import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.QuestionAttempt;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Service.IAnswerService;
import com.swp.online_quizz.Service.IQuestionAttemptsService;
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
    private IQuestionAttemptsService iQuestionAttemptsService;
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
    public List<Question> getAllQuestions() {
        return iQuestionsService.getAllQuestions();
    }

    @GetMapping("/testApi/{quizId}")
    public List<QuizAttempt> testApi(@PathVariable Integer quizId, Model model) {
        User user = iUsersService.getUsersByID(2);
        Quiz quizz = iQuizzesService.getOneQuizz(quizId);
        Timestamp startTime = Timestamp.valueOf("2024-01-25 02:18:35.316");
        return iQuizAttemptsService.findByQuizIdAndUserIdAndStartTime(quizz, user, startTime);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/attemptQuiz/{quizId}")
    public RedirectView attemptQuizz(@PathVariable Integer quizId, Model model) {
        User user = iUsersService.getUsersByID(2);
        if (user != null) {
            Quiz quizz = iQuizzesService.getOneQuizz(quizId);
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            long endTimeMillis = startTime.getTime() + (quizz.getTimeLimit() * 60 * 1000);
            Timestamp endTime = new Timestamp(endTimeMillis);
            long startTimeSearchMillis = startTime.getTime() - (10);
            Timestamp startTimeSearch = new Timestamp(startTimeSearchMillis);
            List<Question> listQuestion = getRandomQuestionsFromSet(quizId, 3); // số lượng câu hỏi trong 1 bài quiz
                                                                                // được tạo
            QuizAttempt newAttemp = new QuizAttempt(0, user, quizz, startTime, endTime,
                    0, false, listQuestion.get(0), null, null, null);
            iQuizAttemptsService.createQuizzAttempt(newAttemp);
            List<QuizProgress> listQProg = new ArrayList<>();
            QuizAttempt attemp = iQuizAttemptsService.findByQuizIdAndUserIdAndStartTime(quizz,
                    user, startTimeSearch).get(0);
            for (int i = 0; i < listQuestion.size(); i++) {
                iQuestionAttemptsService
                        .createQuesstionAttempts(new QuestionAttempt(attemp, listQuestion.get(i), "", false,
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

        User user = iUsersService.getUsersByID(2);
        if (user != null) {
            QuizAttempt attemp = iQuizAttemptsService.getQuizAttempts(attemptID);
            List<QuizProgress> listQProg = new ArrayList<>(attemp.getListQuizzProgress());
            for (QuizProgress quizProgress : listQProg) {
                if (quizProgress.getQuestionOrder() == page) {
                    attemp.setCurrentQuestion(quizProgress.getQuestion());
                    iQuizAttemptsService.updateAttempts(attemptID, attemp);
                }
            }
            page -= 1;
            Question question = listQProg.get((page)).getQuestion();
            QuizProgress quizProgress = listQProg.get((page));
            String answerString = quizProgress.getAnswer();
            int answerProg = 0;
            if (answerString != null && !answerString.isEmpty()) {
                answerProg = Integer.parseInt(answerString);
            } else {
                answerProg = 0;
            }
            Quiz quiz = attemp.getQuiz();
            page += 1;
            model.addAttribute("answerProg", answerProg);
            model.addAttribute("page", page);
            model.addAttribute("attemp", attemp);
            model.addAttribute("quiz", quiz);
            model.addAttribute("question", question);
            model.addAttribute("listQProg", listQProg);
            model.addAttribute("startTime", attemp.getStartTime());
            model.addAttribute("endTime", attemp.getEndTime().getTime());

            model.addAttribute("QuizProgress", new QuizProgress());
            return "doQuizz";
        } else {
            return "Login";
        }
    }

    @PostMapping("/progress")
    public RedirectView progress(@ModelAttribute() QuizProgress progress,
            @RequestParam(name = "previous", required = false) String previous,
            @RequestParam(name = "next", required = false) String next,
            @RequestParam(name = "attempID", required = false) String attempIDString,
            @RequestParam(name = "page", required = false) String pageString,
            @RequestParam(name = "questionID", required = false) String questionIDString) {
        String answerProgress = progress.getAnswer();
        Integer questionID = Integer.parseInt(questionIDString);
        Integer page = Integer.parseInt(pageString);
        Question questionProgress = iQuestionsService.getQuestions(questionID);
        Integer attempID = Integer.parseInt(attempIDString);
        QuizAttempt attemp = iQuizAttemptsService.getQuizAttempts(attempID);
        for (QuestionAttempt questionAttempts : attemp.getListQuestionAttempts()) {
            if (questionAttempts.getQuestion().getQuestionId() == questionProgress.getQuestionId()) {
                if (answerProgress != null) {
                    if (iAnswerService.getAnswers(Integer.parseInt(answerProgress)).getIsCorrect()) {
                        questionAttempts.setIsCorrect(true);
                    } else {
                        questionAttempts.setIsCorrect(false);
                    }
                }
                questionAttempts.setAnswer(answerProgress);
                questionAttempts.setIsAnswered(true);
                iQuestionAttemptsService.updateQuesstionAttempts(questionAttempts.getQuestionAttemptID(),
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
            if (page == 1) {
                page = attemp.getListQuizzProgress().size();
            } else if (page > 1) {
                page -= 1;
            }
            return new RedirectView(
                    "/attempt/attemptQuiz/" + attemp.getQuiz().getQuizId() + "/" + attemp.getAttemptId() + "/"
                            + (page));
        }
        if (next != null) {
            if (page < attemp.getListQuizzProgress().size()) {
                page += 1;
            } else if (page == attemp.getListQuizzProgress().size()) {
                page = 1;
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
        QuizAttempt attempt = iQuizAttemptsService.getQuizAttempts(attemptID);
        attempt.setIsCompleted(true);
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        attempt.setEndTime(endTime);
        int count = 0;
        for (QuestionAttempt questionAttempts : attempt.getListQuestionAttempts()) {
            if (questionAttempts.getIsCorrect() == true) {
                count++;
            }
        }
        double mark = ((double) count / attempt.getListQuestionAttempts().size()) * 10;
        attempt.setMarks((int) mark);
        iQuizAttemptsService.updateAttempts(attemptID, attempt);
        return new RedirectView("/quizzes/{quizId}");
    }

    public List<Question> getRandomQuestionsFromSet(@PathVariable Integer quizId, @PathVariable Integer n) {
        Set<Question> questionSet = iQuizzesService.getOneQuizz(quizId).getListQuestions();
        List<Question> questionList = new ArrayList<>(questionSet);
        Collections.shuffle(questionList);
        List<Question> randomQuestions = questionList.subList(0, Math.min(n, questionList.size()));
        return randomQuestions;
    }
}
