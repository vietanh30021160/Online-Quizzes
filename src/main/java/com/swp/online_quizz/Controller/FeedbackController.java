package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.FeedbackRepository;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.IFeedbackService;
import com.swp.online_quizz.Service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/feedback")
public class FeedbackController {
    private final IFeedbackService iFeedbackService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private QuizAttemptsRepository quizAttemptsRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    public FeedbackController(IFeedbackService feedbackService) {
        this.iFeedbackService = feedbackService;
    }

    @PostMapping("/createFeedback/{attemptID}")
    public String createFeedback(@ModelAttribute("feedback") Feedback feedback,
                                 @PathVariable Integer attemptID,
                                 HttpServletRequest request) {


        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }


        User user = userOptional.get();
        QuizAttempt existingQuizAttempt = quizAttemptsRepository.findByAttemptId(attemptID);
        if (existingQuizAttempt == null) {

            return "redirect:/login";
        }

        feedback.setUser(user);
        feedback.setAttempt(existingQuizAttempt);


        iFeedbackService.createFeedback(feedback);

        return "redirect:/class/mark/"+existingQuizAttempt.getQuiz().getQuizId()+"/attempt/"+attemptID;
    }

    @PutMapping("/updateFeedback/{feedbackID}")
    public String updateFeedback(@PathVariable Integer feedbackID,
                                 @PathVariable Integer attemptID,
                                 @ModelAttribute("feedback") Feedback feedback) {
        QuizAttempt existingQuizAttempt = quizAttemptsRepository.findByAttemptId(attemptID);
        boolean updated = iFeedbackService.updateFeedback(feedbackID, feedback);
        if (updated) {
            return "redirect:/class/mark/"+existingQuizAttempt.getQuiz().getQuizId()+"/attempt/"+attemptID;
        } else {
            return "redirect:/login";
        }
    }

}
