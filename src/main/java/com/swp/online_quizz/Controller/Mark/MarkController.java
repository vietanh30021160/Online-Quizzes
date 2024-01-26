package com.swp.online_quizz.Controller.Mark;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Service.QuizAttemptsService;
import com.swp.online_quizz.Service.QuizzesService;
import com.swp.online_quizz.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
@GetMapping("/mark")
    public String index(Model model, @Param("usename")String usename, @RequestParam(value = "pageNo" , defaultValue = "1") Integer pageNo){
    Page<QuizAttempts> listQuizAttempts = this.quizAttemptsService.getAll(pageNo);
    if(usename != null){
        listQuizAttempts = this.quizAttemptsService.searchUseByName(usename,pageNo);
        model.addAttribute("usename",usename);
    }

    Quizzes QuizzAndSubjectById = (Quizzes) this.quizzesService.QuizzAndSubjectBySubjectID(2);
    model.addAttribute("QuizAttempts",listQuizAttempts);
    model.addAttribute("QuizzAndSubjectById",QuizzAndSubjectById);
    model.addAttribute("totalPage",listQuizAttempts.getTotalPages());
    model.addAttribute("currentPage",pageNo);

        return "class/mark";
    }
}
