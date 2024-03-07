package com.swp.online_quizz.Service;

import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.Feedback;

public interface IFeedbackService {

    @Transactional
    void deleteFeedbackByAttemptId(Integer attemptId);

    @Transactional
    Feedback createFeedback(Feedback feedback);
}
