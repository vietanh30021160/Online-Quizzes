package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import com.swp.online_quizz.Dto.CreateQuizzByQuestionBank;
import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Service.*;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;

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
    private IFeedbackService iFeedbackService;
    @Autowired
    private IClassQuizzService iClassQuizzService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ISubjectService iSubjectService;

    @GetMapping("/all")

    public List<Quiz> getAll() {
        return iQuizzesService.getAll();
    }

    @GetMapping("/list")
    public String showQuizList(Model model, HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        // nếu có thì lấy ra user
        int userId = userOptional.get().getUserId();
        List<Quiz> quizList = iQuizzesService.getQuizByUserId(userId); // Thay thế bằng phương thức lấy danh sách quiz từ Service
        model.addAttribute("quizList", quizList);
        return "showQuiz";
    }

    @Transactional
    @PostMapping("/createAll")
    public String createQuizWithQuestionsAndAnswers(@ModelAttribute("quiz") Quiz quiz, HttpServletRequest request) {

        String subjectName = quiz.getSubjectName();


        Subject subject = new Subject();
        subject.setSubjectName(subjectName);


        quiz.setSubject(subject);
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        } // nếu có thì lấy ra user
        int userId = userOptional.get().getUserId();
        User defaultTeacher = iUsersService.getUsersByID(userId);
        quiz.setTeacher(defaultTeacher);
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
        model.addAttribute("quiz", new Quiz());
        List<String> questionContents = quiz.getListQuestions().stream()
                .map(Question::getQuestionContent)
                .collect(Collectors.toList());

        model.addAttribute("questionContents", questionContents);

        return "createQuiz";
    }

    @GetMapping("/createQuizByListQuestions")
    public String showCreateQuizzPageByListQuestion(Model model,@RequestParam(value = "question",required = false) String question) {
        List<Question> questions ;
        if(question!=null){
            questions = this.iQuestionsService.getALlQuestionBySearch(question);
            model.addAttribute("question",question);
        }
        questions = this.iQuestionsService.getAllQuestionUnique();
        model.addAttribute("ListQuestion", questions);
        model.addAttribute("formObject", new CreateQuizzByQuestionBank());
        return "CreateQuizzByBankQuestion";
    }

    @Transactional
    @PostMapping("/createQuizByListQuestion")
    public String createQuizWithListQuestions(@ModelAttribute("formObject") CreateQuizzByQuestionBank formObject,
                                              HttpServletRequest request) {
        Quiz quiz = formObject.getQuiz();
        String subjectName = quiz.getSubjectName();
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        quiz.setSubject(subject);
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        int userId = userOptional.get().getUserId();
        User defaultTeacher = iUsersService.getUsersByID(userId);
        quiz.setTeacher(defaultTeacher);
        boolean quizCreated = iQuizService.createQuiz1(quiz);

        Integer quizId = quiz.getQuizId();
        List<Question> questions = iQuestionsService.getQuestionsByIds(formObject.getSelectedQuestions());
        if (quizCreated) {

            for (Question question : questions) {
                question.setQuiz(quiz);
                iQuestionsService.createQuestion1(question);
                for (Answer answer : question.getListAnswer()) {
                    answer.setQuestion(question);
                    iAnswerService.createAnswer1(answer, question.getQuestionId());
                }
            }
            return "redirect:/quizzes/list";
        } else {
            return "redirect:/quizzes/createQuizByListQuestion";
        }
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
        }

        return "redirect:/quizzes/list";
    }

    @GetMapping("/showUpdateQuizPage/{quizId}")
    public String getUpdateQuizForm(@PathVariable Integer quizId, Model model) {

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
            List<QuizAttempt> attempts = iQuizAttemptsService.getQuizAttemptsByQuizId(quizId);
            for (QuizAttempt attempt : attempts) {
                iFeedbackService.deleteFeedbackByAttemptId(attempt.getAttemptId());
            }
            iQuizAttemptsService.deleteQuizAttemptsByQuizId(quizId);
            iClassQuizzService.deleteClassQuizzByQuizId(quizId);
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
    public String quizInfo(@PathVariable Integer quizID, HttpSession session, Model model, Authentication auth, HttpServletRequest request) {

        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            //Nếu không có user thì làm gì đấy
            return "redirect:/login";
        }
        //nếu có thì lấy ra user
        User user1 = userOptional.get();

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
