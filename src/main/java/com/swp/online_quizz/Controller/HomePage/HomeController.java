package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IQuizzesService;
import com.swp.online_quizz.Service.ISubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private ISubjectsService subjectsService;
    @Autowired
    private IQuizzesService quizzesService;
    @RequestMapping
    public String Home(Model model){
        List<Subject> listSubject = subjectsService.getAll();
        List<Quiz> listQuiz = quizzesService.getAll();
        model.addAttribute("listSubjects",listSubject);
        model.addAttribute("listQuizzes",listQuiz);
        return "html/index";
    }

}
