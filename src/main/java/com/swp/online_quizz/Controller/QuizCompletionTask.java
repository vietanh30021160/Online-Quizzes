package com.swp.online_quizz.Controller;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;

@Component
public class QuizCompletionTask {

    private final QuizAttemptsRepository quizAttemptRepository;
    private final QuizAttemptController quizAttemptController;

    public QuizCompletionTask(QuizAttemptsRepository quizAttemptRepository,
            QuizAttemptController quizAttemptController) {
        this.quizAttemptRepository = quizAttemptRepository;
        this.quizAttemptController = quizAttemptController;
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void completeExpiredQuizzes() {
        List<QuizAttempt> expiredQuizAttempts = quizAttemptRepository.findExpiredIncompleteQuizzes();
        for (QuizAttempt quizAttempt : expiredQuizAttempts) {
            quizAttemptController.finishQuizAttempt(quizAttempt.getQuiz().getQuizId(), quizAttempt.getAttemptId());
        }
    }
}
