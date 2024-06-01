package com.swp.online_quizz.Controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import com.swp.online_quizz.Service.IQuizAttemptsService;

@Component
public class QuizCompletionTask {

    private final QuizAttemptsRepository quizAttemptRepository;
    private final IQuizAttemptsService quizAttemptsService;
    private boolean hasNewQuizAttempts = false;

    public QuizCompletionTask(QuizAttemptsRepository quizAttemptRepository,
            IQuizAttemptsService quizAttemptsService) {
        this.quizAttemptRepository = quizAttemptRepository;
        this.quizAttemptsService = quizAttemptsService;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void completeExpiredQuizzes() {
        if (!hasNewQuizAttempts) {
            return;
        }

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        List<QuizAttempt> expiredQuizAttempts = quizAttemptRepository.findExpiredIncompleteQuizzes(false, currentTime);
        if (!expiredQuizAttempts.isEmpty()) {
            for (QuizAttempt quizAttempt : expiredQuizAttempts) {
                quizAttemptsService.finishQuizAttempt(quizAttempt.getQuiz().getQuizId(), quizAttempt.getAttemptId());
            }
        } else {
            hasNewQuizAttempts = false;
        }

    }

    public void setHasNewQuizAttempts(boolean hasNewQuizAttempts) {
        this.hasNewQuizAttempts = hasNewQuizAttempts;
    }
}
