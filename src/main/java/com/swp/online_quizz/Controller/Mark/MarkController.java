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
import org.springframework.data.domain.Sort;
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
                        @PathVariable("quizzId") Integer quizzId, @RequestParam(value = "sort", defaultValue = "marks") String sort, @RequestParam(value = "dir", defaultValue = "asc") String dir) {
        Sort s = Sort.by(Sort.Direction.fromString(dir), sort);
        Page<QuizAttempt> listQuizAttempts = this.quizAttemptsService.findQuizAttemptsByQuizID(quizzId, pageNo, s);
        int mark_low = 0, mark_medium = 0, mark_high = 0, index = 0;
        for (QuizAttempt quizAttempt : listQuizAttempts) {
            index = index + 1;
            if (quizAttempt.getMarks() >= 0 && quizAttempt.getMarks() < 4) {
                mark_low += 1;
            } else if (quizAttempt.getMarks() >= 4 && quizAttempt.getMarks() < 8) {
                mark_medium += 1;
            } else {
                mark_high += 1;
            }
        }
        int pec_low = mark_low / index;
        int pec_medium = mark_medium / index;
        int pec_high = mark_high / index;

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
        model.addAttribute("mark_low", pec_low);
        model.addAttribute("mark_medium", pec_medium);
        model.addAttribute("mark_high", pec_high);
        return "mark";
    }

    @GetMapping("mark/{quizzId}/attempt/{attemptID}")
    public String reviewAttempt(Model model, @PathVariable(value = "quizzId") Integer quizzId, @PathVariable("attemptID") Integer attemptID) {
        QuizAttempt quizAttempts = this.quizAttemptsService.findById(attemptID);
        List<QuestionAttempt> questionAttemptList = this.questionAttemptsService.findByAttemptID(attemptID);
        model.addAttribute("QuizAttempts", quizAttempts);
        model.addAttribute("QuestionAttemptsList", questionAttemptList);

        return "ReviewMark";
    }
}
