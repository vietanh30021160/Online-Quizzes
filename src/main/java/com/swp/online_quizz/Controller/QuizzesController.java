package com.swp.online_quizz.Controller;

import java.util.List;

import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
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

            // Redirect to a success page or do further processing
            return "redirect:/quizzes/list";
        } else {
            // Handle the case where the quiz creation failed
            return "redirect:/quizzes/createAll";
        }
    }
    @GetMapping("/showCreateQuizPage")
    public String showCreateQuizPage(Model model) {
        model.addAttribute("quiz",new Quiz());
        return "createQuiz";
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
