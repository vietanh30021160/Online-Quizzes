package com.swp.online_quizz.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp.online_quizz.Entity.Questions;
import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Service.IQuizzesService;

@RestController
@RequestMapping(path = "/quizzes")
public class QuizzesController {
    @Autowired
    private IQuizzesService iQuizzesService;

    @GetMapping("/")
    public String test() {
        return "Hello";
    }

    @GetMapping("/list")
    public List<Quizzes> getAllQuizz() {
        return iQuizzesService.getAllQuizzes();
    }

    @GetMapping("/list/{quizId}")
    public Quizzes getOneQuizz(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId);
    }

    @GetMapping("/question/{quizId}")
    public Set<Questions> getListSubjects(@PathVariable Integer quizId) {
        return iQuizzesService.getOneQuizz(quizId).getListQuestions();
    }
}
