package com.swp.online_quizz.Service;

import org.springframework.transaction.annotation.Transactional;

public interface IFeedbackService {

    @Transactional
    void deleteFeedbackByAttemptId(Integer attemptId);
}
