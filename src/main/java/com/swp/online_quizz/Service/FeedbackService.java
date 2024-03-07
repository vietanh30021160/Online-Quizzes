package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.FeedbackRepository;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;

@Service
public class FeedbackService implements IFeedbackService {
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
    public Feedback createFeedback(Feedback feedback) {
        try {
            QuizAttempt existingQuizAttempt = quizAttemptsRepository
                    .findByAttemptId(feedback.getAttempt().getAttemptId());

            User user = iUsersService.getUsersByID(feedback.getUser().getUserId());
            feedback.setAttempt(existingQuizAttempt);
            feedback.setUser(user);
            return feedbackRepository.save(feedback);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
