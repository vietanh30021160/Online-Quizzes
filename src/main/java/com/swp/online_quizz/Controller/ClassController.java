package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.ClassesService;
import com.swp.online_quizz.Service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/Classes")
public class ClassController {
    @Autowired
    private ClassesService classesService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IUsersService iUsersService;

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
            listClasses = this.classesService.searchClassesByClassesNameAndUserID(className, pageNo, userOptional.get().getUserId());
            model.addAttribute("className", className);
        }
        model.addAttribute("totalPage", listClasses.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("username", username); /// lấy class đầu tiên để lấy teacher
        model.addAttribute("ListClasses", listClasses);
        return "listClasses";
    }

    @GetMapping("/addClass")
    public String addClass(Model model) {
        Classes classes = new Classes();
        model.addAttribute("classes", classes);

        return "addClass";
    }

    @PostMapping("/addClass")
    public String save(@ModelAttribute("classes") Classes classes, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        Integer userID = userOptional.get().getUserId();

        User defaultTeacher = iUsersService.getUsersByID(userID);
        classes.setTeacher(defaultTeacher);
        classes.setClassName(classes.getClassName());

        if (this.classesService.createClass(classes)) {
            redirectAttributes.addFlashAttribute("mss", "Add Classes Success");
            return "redirect:/Classes/listClasses";
        } else {
            return "addClass";
        }
    }

    @GetMapping("/updateNameClass/{id}")
    public String update(Model model, @PathVariable("id") Integer id) {
        Classes classes = this.classesService.findById(id);
        model.addAttribute("classes", classes);
        return "updateClass";
    }

    @PostMapping("/updateNameClass")
    public String update(@ModelAttribute("classes") Classes classes, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        Integer userID = userOptional.get().getUserId();

        User defaultTeacher = iUsersService.getUsersByID(userID);
        Classes classesID = classesService.findById(classes.getClassId());
        classes.setTeacher(defaultTeacher);
        classes.setClassName(classes.getClassName());
        classes.setClassCode(classesID.getClassCode());
        if (this.classesService.updateClass(classes)) {
            redirectAttributes.addFlashAttribute("mss", "Update Classes Success");
            return "redirect:/Classes/listClasses";
        } else {
            return "addClass";
        }
    }

    @GetMapping("/deleteClasses/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (this.classesService.deleteClass(id)) {
            redirectAttributes.addFlashAttribute("mss", "Delete Classes Success");
        } else {
            redirectAttributes.addFlashAttribute("mss", "Delete Classes Unsucces!!!");
        }
        return "redirect:/Classes/listClasses";

    }
}