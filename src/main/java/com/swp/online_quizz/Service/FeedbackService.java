package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.*;
import com.swp.online_quizz.Repository.FeedbackRepository;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FeedbackService implements IFeedbackService{
    @Autowired
    public FeedbackRepository feedbackRepository;
    @Autowired
    public QuizAttemptsRepository quizAttemptsRepository;
    @Autowired
    public IUsersService iUsersService;
    @Override
    @Transactional
    public void deleteFeedbackByAttemptId(Integer attemptId) {
        List<Feedback> feedbackList = feedbackRepository.findByAttemptAttemptId(attemptId);
        feedbackRepository.deleteAll(feedbackList);
    }


    @Transactional
    @Override
    public boolean createFeedback(Feedback feedback) {
        try {
            QuizAttempt existingQuizAttempt = quizAttemptsRepository.findByAttemptId(feedback.getAttempt().getAttemptId());

            User user = iUsersService.getUsersByID(feedback.getUser().getUserId());
            feedback.setAttempt(existingQuizAttempt);
            feedback.setUser(user);
            feedbackRepository.save(feedback);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
