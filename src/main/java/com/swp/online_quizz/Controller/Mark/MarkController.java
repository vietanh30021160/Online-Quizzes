package com.swp.online_quizz.Controller.Mark;

import com.swp.online_quizz.Entity.QuestionAttempt;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IQuestionAttemptsService;
import com.swp.online_quizz.Service.IQuizAttemptsService;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/mark/{quizzId}")
    public String index( Model model, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                        @PathVariable("quizzId") Integer quizzId, @RequestParam(value = "sort", defaultValue = "marks") String sort, @RequestParam(value = "dir", defaultValue = "asc") String dir) {

        Sort s = Sort.by(Sort.Direction.fromString(dir), sort);
        Page<QuizAttempt> listQuizAttempts = this.quizAttemptsService.findQuizAttemptsByQuizID(quizzId, pageNo, s);
        List<QuizAttempt> ListQuizAttemptsSatic = this.quizAttemptsService.findQuizAttemptsByQuizID(quizzId,s);
        int mark_low = 0, mark_medium = 0, mark_high = 0, index = 0;
        for (QuizAttempt quizAttempt : ListQuizAttemptsSatic) {
            index = index + 1;
            if (quizAttempt.getMarks() >= 0 && quizAttempt.getMarks() < 4) {
                mark_low += 1;
            } else if (quizAttempt.getMarks() >= 4 && quizAttempt.getMarks() < 8) {
                mark_medium += 1;
            } else {
                mark_high += 1;
            }
        }
        double pec_low = Math.round((double) mark_low * 100 / index * 100.0) / 100.0;
        double pec_medium = Math.round((double) mark_medium * 100 / index * 100.0) / 100.0;
        double pec_high = (double) 100 - pec_low - pec_medium;

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
        model.addAttribute("stt_low", mark_low);
        model.addAttribute("stt_medium", mark_medium);
        model.addAttribute("stt_high", mark_high);
        return "mark";
    }

    @GetMapping("mark/{quizzId}/attempt/{attemptID}")
    public String reviewAttempt(HttpServletRequest request,Model model, @PathVariable(value = "quizzId") Integer quizzId, @PathVariable("attemptID") Integer attemptID) {
        QuizAttempt quizAttempts = this.quizAttemptsService.findById(attemptID);
        List<QuestionAttempt> questionAttemptList = this.questionAttemptsService.findByAttemptID(attemptID);
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        model.addAttribute("useRole",userOptional.get().getRole());
        model.addAttribute("QuizAttempts", quizAttempts);
        model.addAttribute("QuestionAttemptsList", questionAttemptList);

        return "ReviewMark";
    }
}
