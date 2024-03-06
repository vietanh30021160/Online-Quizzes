package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private ClassEnrollmentService
            classEnrollmentService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ClassQuizzService classQuizzService;
    @Autowired
    private IQuizzesService iQuizzesService;
    @Autowired
    private QuestionsService questionsService;
    @Autowired
    private AnswerService answerService;

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
        List<Classes> TotalClass = this.classesService.getAllClassByUserId(userOptional.get().getUserId());
        if (className != null) {
            listClasses = this.classesService.searchClassesByClassesNameAndUserID(className, pageNo, userOptional.get().getUserId());
            model.addAttribute("className", className);
        }
        model.addAttribute("TotalClass", TotalClass.size());
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

    @GetMapping("listClasses/{classId}")
    public String listStudentinClass(Model model, @PathVariable("classId") Integer classId, @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                     @RequestParam(value = "firstName", required = false) String firstName, HttpServletRequest request) {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        Page<ClassEnrollment> listStudent = this.classEnrollmentService.getAllStudentByClassId(classId, pageNo);
        List<ClassEnrollment> listStudentSize = this.classEnrollmentService.getAllStudentByClassId(classId);
        List<ClassQuizz> listQuizInClass = this.classQuizzService.getQuizByClassId(classId);

        if (firstName != null) {
            listStudent = this.classEnrollmentService.getAllStudentBySearch(classId, firstName, pageNo);
        }
        model.addAttribute("listQuizInClass", listQuizInClass);
        model.addAttribute("listStudents", listStudent);
        model.addAttribute("size", listStudentSize.size());
        model.addAttribute("nameTeacher", username);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", listStudent.getTotalPages());
        model.addAttribute("classId", classId);

        return "ListStudentInClass";
    }

    @GetMapping("listClasses/{classId}/profile/{useID}")
    public String profileStudent(Model model, @PathVariable("classId") Integer classId, @PathVariable("useID") Integer useID) {
        User user = this.usersRepository.findByuserId(useID);
        model.addAttribute("profileStudent", user);
        model.addAttribute("classId", classId);
        return "inforStudentInClass";
    }

    @GetMapping("listClasses/{classId}/addStudent")
    public String addStudent(Model model, @PathVariable("classId") Integer classId) {
        User user = new User();
        model.addAttribute("userStudent", user);
        model.addAttribute("classId", classId);

        return "addStudent";
    }

    @PostMapping("listClasses/{classId}/addStudent")
    public String AddStudentInClass(Model model, @PathVariable("classId") Integer classId, @ModelAttribute("userStudent") User user, RedirectAttributes redirectAttributes) {
        Classes classes = this.classesService.findById(classId);
        User user1 = this.usersService.getUsersByID(user.getUserId());
        ClassEnrollment addStudent = new ClassEnrollment();
        addStudent.setClasses(classes);
        addStudent.setStudentID(user1);
        if (this.classEnrollmentService.isAddStudentInClass(addStudent)) {
            redirectAttributes.addFlashAttribute("mss", "Update Student Success");
            return "redirect:/Classes/listClasses/{classId}";
        } else {
            return "addStudent";
        }

    }

    @GetMapping("listClasses/{classId}/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (this.classEnrollmentService.deleteStudentInClass(id)) {
            redirectAttributes.addFlashAttribute("mss", "Delete Student Success");
        } else {
            redirectAttributes.addFlashAttribute("mss", "Delete Student Unsucces!!!");
        }
        return "redirect:/Classes/listClasses/{classId}";

    }

    @GetMapping("/listClasses/{classId}/createQuizz")
    public String ShowCreateQuizInClass(Model model,
                                        @RequestParam(value = "question", required = false) String question,
                                        @PathVariable(value = "classId") Integer classId) {
        Quiz quiz = iQuizzesService.getEmptyQuiz();
        model.addAttribute("quiz", new Quiz());
        List<String> questionContents = quiz.getListQuestions().stream()
                .map(Question::getQuestionContent)
                .collect(Collectors.toList());
        Classes classes = this.classesService.findById(classId);
        model.addAttribute("classes", classes);
        model.addAttribute("questionContents", questionContents);
        return "CreateQuizInClass";
    }

    @Transactional
    @PostMapping("/listClasses/{classId}/createQuizz")
    public String createQuizWithQuestionsAndAnswers(@ModelAttribute("quiz") Quiz quiz, HttpServletRequest request, @PathVariable(value = "classId") Integer classId) {
        Classes classes = new Classes();
        String subjectName = quiz.getSubjectName();
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        quiz.setSubject(subject);
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        } // nếu có thì lấy ra user
        int userId = userOptional.get().getUserId();
        User defaultTeacher = iUsersService.getUsersByID(userId);
        quiz.setTeacher(defaultTeacher);
        ClassQuizz classQuizz = new ClassQuizz();
        boolean quizCreated = iQuizzesService.createQuiz1(quiz);
        classes= this.classesService.getClassByClassId(classId);
        if (quizCreated) {

            for (Question question : quiz.getListQuestions()) {

                question.setQuiz(quiz);

                questionsService.createQuestion1(question);

                for (Answer answer : question.getListAnswer()) {

                    answer.setQuestion(question);
                    answerService.createAnswer1(answer, question.getQuestionId());
                }
            }
            boolean addQuizzInClass = classQuizzService.isAddQuizInClass(classes, quiz);
            if (addQuizzInClass) {
                return "redirect:/Classes/listClasses/{classId}";
            } else {
                return "createQuizz";
            }
        } else {
            return "createQuizz";
        }
    }
}