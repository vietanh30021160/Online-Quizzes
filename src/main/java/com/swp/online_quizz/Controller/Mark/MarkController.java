package com.swp.online_quizz.Controller.Mark;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Service.QuestionAttemptsService;
import com.swp.online_quizz.Service.QuizAttemptsService;
import com.swp.online_quizz.Service.QuizzesService;
import com.swp.online_quizz.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
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
    private UsersService usersService;
    @Autowired
    private QuizAttemptsService quizAttemptsService;
    @Autowired
    private QuizzesService quizzesService;
    @Autowired
    private QuestionAttemptsService questionAttemptsService;
    @GetMapping("/mark/{quizzId}")
    public String index(Model model, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                        @PathVariable("quizzId") Integer quizzId) {
        Page<QuizAttempts> listQuizAttempts = this.quizAttemptsService.findQuizAttemptsByQuizID(quizzId, pageNo);
        if (username != null) {
            listQuizAttempts = this.quizAttemptsService.searchUseByName(username, quizzId, pageNo);
            model.addAttribute("username", username);
        }

        Quizzes QuizzAndSubjectById = this.quizzesService.findByID(quizzId);
        QuizzAndSubjectById.getSubject();
        model.addAttribute("QuizAttempts", listQuizAttempts);
        model.addAttribute("QuizzAndSubjectById", QuizzAndSubjectById);
        model.addAttribute("totalPage", listQuizAttempts.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        return "class/mark";
    }
    @GetMapping("mark/{quizzId}/attempt/{attemptID}")
    public String reviewAttempt(Model model , @PathVariable(value = "quizzId") Integer quizzId , @PathVariable("attemptID") Integer attemptID){
            QuizAttempts quizAttempts = this.quizAttemptsService.findById(attemptID);
            List<QuestionAttempts> questionAttemptsList = this.questionAttemptsService.findByAttemptID(attemptID);
            model.addAttribute("QuizAttempts",quizAttempts);
            model.addAttribute("QuestionAttemptsList",questionAttemptsList);

            return "class/ReviewMark";
    }
}
