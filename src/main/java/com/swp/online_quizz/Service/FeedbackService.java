package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Feedback;
import com.swp.online_quizz.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class FeedbackService implements IFeedbackService{
    @Autowired
    public FeedbackRepository feedbackRepository;
    @Override
    @Transactional
    public void deleteFeedbackByAttemptId(Integer attemptId) {
        List<Feedback> feedbackList = feedbackRepository.findByAttemptAttemptId(attemptId);
        feedbackRepository.deleteAll(feedbackList);
    }
}
