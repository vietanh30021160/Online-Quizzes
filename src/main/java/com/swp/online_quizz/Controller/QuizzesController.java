package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.stream.Collectors;

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

            // Redirect to a success page or do further processing
            return "redirect:/quizzes/list";
        } else {
            // Handle the case where the quiz creation failed
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
    public String processUpdateQuizForm(@PathVariable Integer quizId, @ModelAttribute Quiz quiz) {

        Quiz oldQuiz = iQuizService.findQuizById(quizId);

        oldQuiz.setQuizName(quiz.getQuizName());
        oldQuiz.setTimeLimit(quiz.getTimeLimit());
        oldQuiz.setListQuestions(quiz.getListQuestions());
        iQuizService.updateQuizByQuizId1(quizId, oldQuiz);

        for (Question question : quiz.getListQuestions()) {
            List<Question> oldQuestion = oldQuiz.getListQuestions();
            question.setQuiz(quiz);
            Question olQuestion = iQuestionsService.findQuestionById(question.getQuestionId());
            if (oldQuestion == null) {
                iQuestionsService.createQuestion1(olQuestion);
                System.out.println("Create successfull!");
            } else {
                olQuestion.setQuestionContent(question.getQuestionContent());
                olQuestion.setQuestionType(question.getQuestionType());
                iQuestionsService.updateQuestion1(question.getQuestionId(), question);


                for (Answer answer : question.getListAnswer()) {

                    answer.setQuestion(question);
                    iAnswerService.updateAnswer1(answer.getAnswerId(), answer);
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
