package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private QuizRepository quizRepository;
    @Autowired
    private IQuizzesService iQuizService;
    @Autowired
    private IQuestionsService iQuestionsService;
    @Autowired
    private IQuizProgressService iQuizProgressService;
    @Autowired
    private IQuestionAttemptsService iQuestionAttemptsService;
    @Autowired
    private IAnswerService iAnswerService;
    @Autowired
    private ISubjectService iSubjectService;
    @GetMapping("/all")

    public List<Quiz> getAll(){
        return iQuizzesService.getAll();
    }

    @GetMapping("/list")
    public String showQuizList(Model model) {
        List<Quiz> quizList = iQuizzesService.getAll(); // Thay thế bằng phương thức lấy danh sách quiz từ Service
        model.addAttribute("quizList", quizList);
        return "showQuiz";
    }
    @Transactional
    @PostMapping("/createAll")
    public String createQuizWithQuestionsAndAnswers(@ModelAttribute("quiz") Quiz quiz

    ) {
//

        String subjectName = quiz.getSubjectName();


        Subject subject = new Subject();
        subject.setSubjectName(subjectName);


        quiz.setSubject(subject);
        if (quiz.getTeacher() == null) {

            User defaultTeacher = iUsersService.getUsersByID(1);
            quiz.setTeacher(defaultTeacher);
        }
        boolean quizCreated = iQuizService.createQuiz1(quiz);

        if (quizCreated) {

            for (Question question : quiz.getListQuestions()) {

                question.setQuiz(quiz);


                iQuestionsService.createQuestion1(question);


                for (Answer answer : question.getListAnswer()) {

                    answer.setQuestion(question);
                    iAnswerService.createAnswer1(answer, question.getQuestionId());
                }
            }
            return "redirect:/quizzes/list";
        } else {
            return "redirect:/quizzes/createAll";
        }
    }
    @GetMapping("/showCreateQuizPage")
    public String showCreateQuizPage(Model model) {
        Quiz quiz = iQuizzesService.getEmptyQuiz();
        model.addAttribute("quiz",new Quiz());
        List<String> questionContents = quiz.getListQuestions().stream()
                .map(Question::getQuestionContent)
                .collect(Collectors.toList());

        model.addAttribute("questionContents", questionContents);

        return "createQuiz";
    }


        @PostMapping("/updateAll/{quizId}")
        public String updateQuizAndQuestions(@PathVariable Integer quizId, @ModelAttribute("quiz") Quiz updatedQuiz) {
            iQuizzesService.updateQuizByQuizId1(quizId, updatedQuiz);

            for (Question updatedQuestion : updatedQuiz.getListQuestions()) {
                Question existingQuestion = iQuestionsService.findQuestionById(updatedQuestion.getQuestionId());

                if (existingQuestion != null) {
                    iQuestionsService.updateQuestion1(existingQuestion.getQuestionId(), updatedQuestion);
                } else {
                    updatedQuestion.setQuiz(updatedQuiz);
                    iQuestionsService.createQuestion1(updatedQuestion);
                }

                for (Answer updatedAnswer : updatedQuestion.getListAnswer()) {
                    Answer existingAnswer = iAnswerService.getAnswerById(updatedAnswer.getAnswerId());

                    if (existingAnswer != null) {
                        iAnswerService.updateAnswer1(existingAnswer.getAnswerId(), updatedAnswer);
                    } else {
                        updatedAnswer.setQuestion(updatedQuestion);
                        iAnswerService.createAnswer1(updatedAnswer, updatedQuestion.getQuestionId());
                    }
                }

            return "redirect:/quizzes/list";

        }
        return "redirect:/quizzes/list";
    }
    @GetMapping("/showUpdateQuizPage/{quizId}")
    public String getUpdateQuizForm(@PathVariable Integer quizId, Model model) {
        // Retrieve the quiz and its details from the service
        Quiz quiz = iQuizService.findQuizById(quizId);
        if (quiz == null) {
            return "showQuiz";
        }
        model.addAttribute("quiz", quiz);

        return "updateQuiz";
    }
    @GetMapping("/delete/{quizId}")
    public String deleteQuiz(@PathVariable Integer quizId) {
        try {
            iQuizProgressService.deleteQuizProcessByQuizId(quizId);
            iQuestionAttemptsService.deleteQuestionAttemptsByQuizId(quizId);
            iQuizAttemptsService.deleteQuizAttemptsByQuizId(quizId);
            iQuestionsService.deleteQuestionsByQuizId(quizId);
            List<Question> questions = iQuestionsService.getQuestionsByQuizId(quizId);
            for (Question question : questions) {
                iAnswerService.deleteAnswersByQuestionId(question.getQuestionId());
            }

            iQuizzesService.deleteQuizById(quizId);
            return "redirect:/quizzes/list";
        } catch (Exception e) {
            return "redirect:/quizzes/list";
        }
    }
    @GetMapping("/{quizID}")
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model) {
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
