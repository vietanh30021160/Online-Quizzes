package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QuestionAttemptsService {
    List<QuestionAttempts> findByAttemptID(Integer attemptID);
}
