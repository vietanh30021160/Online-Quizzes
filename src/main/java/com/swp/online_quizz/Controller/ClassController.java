package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.ClassesService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/Classes")
public class ClassController {
    @Autowired
    private ClassesService classesService;
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/listClasses")
    public String index(Model model, @RequestParam(value = "className", required = false) String className, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo
            , HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        Page<Classes> listClasses = this.classesService.getAllClassByUserId(userOptional.get().getUserId(), pageNo);
        if (className != null) {
            listClasses = this.classesService.searchClassesByClassesNameAndUserID(className, pageNo, 2);
            model.addAttribute("className", className);
        }
        Classes classesFirst = listClasses.getContent().get(0);
        model.addAttribute("totalPage", listClasses.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("classesFirst", classesFirst); /// lấy class đầu tiên để lấy teacher
        model.addAttribute("ListClasses", listClasses);
        return "listClasses";
    }

    @GetMapping("/addClass")
    public String addClass() {

        return "addClass";
    }
}
