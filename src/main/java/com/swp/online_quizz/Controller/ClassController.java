package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Service.ClassesService;
import com.swp.online_quizz.Service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/Classes")
public class ClassController {
    @Autowired
    private ClassesService classesService;

    @GetMapping("/listClasses")
    public String index(Model model, @RequestParam(value = "className", required = false) String className, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo) {

        Page<Classes> listClasses = this.classesService.getAll(pageNo);
//        if (className != null) {
//            listClasses = this.classesService.searchClassesByClassesName(className);
//            model.addAttribute("className", className);
//        }
        model.addAttribute("totalPage",listClasses.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("ListClasses", listClasses);
        return "listClasses";
    }
}
