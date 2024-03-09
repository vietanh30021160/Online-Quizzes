package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Feedback;
import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.Feedback;

public interface IFeedbackService {

    @Transactional
    void deleteFeedbackByAttemptId(Integer attemptId);

    @Transactional
    void deleteFeedbackByFeedbackId(Integer feedbackId);

    @Transactional
    Feedback createFeedback(Feedback feedback);

    @Transactional
    Boolean updateFeedback(Integer id, Feedback feedback);

}
