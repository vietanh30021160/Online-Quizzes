package com.swp.online_quizz.Controller.Mark;

import com.swp.online_quizz.Entity.QuestionAttempt;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Service.IQuestionAttemptsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/class")
public class MarkController {
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IQuizAttemptsService quizAttemptsService;
    @Autowired
    private IQuizzesService quizzesService;
    @Autowired
    private IQuestionAttemptsService questionAttemptsService;
    @GetMapping("/mark/{quizzId}")
    public String index(Model model, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                        @PathVariable("quizzId") Integer quizzId) {
        Page<QuizAttempt> listQuizAttempts = this.quizAttemptsService.findQuizAttemptsByQuizID(quizzId, pageNo);
        if (username != null) {
            listQuizAttempts = this.quizAttemptsService.searchUseByName(username, quizzId, pageNo);
            model.addAttribute("username", username);
        }

        Quiz QuizzAndSubjectById = this.quizzesService.findByID(quizzId);
        QuizzAndSubjectById.getSubject();
        model.addAttribute("QuizAttempts", listQuizAttempts);
        model.addAttribute("QuizzAndSubjectById", QuizzAndSubjectById);
        model.addAttribute("totalPage", listQuizAttempts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "mark";
    }
    @GetMapping("mark/{quizzId}/attempt/{attemptID}")
    public String reviewAttempt(Model model , @PathVariable(value = "quizzId") Integer quizzId , @PathVariable("attemptID") Integer attemptID){
            QuizAttempt quizAttempts = this.quizAttemptsService.findById(attemptID);
            List<QuestionAttempt> questionAttemptList = this.questionAttemptsService.findByAttemptID(attemptID);
            model.addAttribute("QuizAttempts",quizAttempts);
            model.addAttribute("QuestionAttemptsList", questionAttemptList);

            return "ReviewMark";
    }
}
