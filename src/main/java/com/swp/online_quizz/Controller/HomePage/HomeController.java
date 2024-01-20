package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Subjects;
import com.swp.online_quizz.Service.QuizzesService;
import com.swp.online_quizz.Service.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private SubjectsService subjectsService;
    @Autowired
    private QuizzesService quizzesService;
    @RequestMapping("")
    public String Home(Model model){
        List<Subjects> listSubject = subjectsService.getAll();
        List<Quizzes> listQuiz = quizzesService.getAll();
        model.addAttribute("listSubjects",listSubject);
        model.addAttribute("listQuizzes",listQuiz);
        return "Html/index.html";
    }
}
