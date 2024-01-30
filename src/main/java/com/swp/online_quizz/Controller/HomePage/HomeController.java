package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.CustomUserDetails;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller

public class HomeController {
    @Autowired
    private ISubjectService iSubjectService;
    @Autowired
    private IQuizzesService iQuizzesService;

    @RequestMapping("")
    public String Home(Model model,
                       @Param("keyword") String keyword,
                       @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) Integer min,
                       @RequestParam(required = false) Integer max) {
        List<Subject> listSubject = iSubjectService.getAll();
        Page<Quiz> listQuiz =  iQuizzesService.getAll(pageNo);
        if(min != null && max != null && keyword != null){
            listQuiz = iQuizzesService.searchAndFilter(keyword,pageNo,min,max);

        }
        if(keyword != null && min == null && max == null){
            listQuiz = iQuizzesService.searchQuizzes(keyword,pageNo);
        }
        if(keyword == null && min != null && max != null){
            listQuiz = iQuizzesService.searchAndFilter(keyword,pageNo,min,max);
        }
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPage",listQuiz.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("listSubject", listSubject);
        model.addAttribute("listQuiz", listQuiz);
        return "HomePage";
    }

}
