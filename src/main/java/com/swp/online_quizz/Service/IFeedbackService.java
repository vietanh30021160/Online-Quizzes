package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Feedback;
import org.springframework.transaction.annotation.Transactional;

public interface IFeedbackService {

    @Transactional
    void deleteFeedbackByAttemptId(Integer attemptId);

    @Transactional
    boolean createFeedback(Feedback feedback);
}
