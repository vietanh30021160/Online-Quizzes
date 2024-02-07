package com.swp.online_quizz.Controller.HomePage;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Service.IClassesService;
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


@RestController

public class HomeController {
    @Autowired
    private ISubjectService iSubjectService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private IClassesService iClassesService;

    @RequestMapping("")
    public String Home(Model model,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String subject,
                       @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                       @RequestParam(required = false) Integer min,
                       @RequestParam(required = false) Integer max) {
        List<Subject> listSubject = iSubjectService.getAll();
        Page<Quiz> listQuiz ;
        listQuiz = iQuizzesService.searchAndFilterAndSubject(keyword,pageNo,min,max,subject);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("keyword", keyword);
        model.addAttribute("subject", subject);
        model.addAttribute("totalPage",listQuiz.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("listSubject", listSubject);
        model.addAttribute("listQuiz", listQuiz);
        return "HomePage";
    }
    @GetMapping("/join")
    public ResponseEntity<String> joinClass(@RequestParam String classCode, @RequestParam Integer studentId) {
        iClassesService.joinClass(classCode, studentId);
        return ResponseEntity.ok("Joined class successfully");
    }


}
