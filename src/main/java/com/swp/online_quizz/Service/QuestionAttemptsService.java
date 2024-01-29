package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempt;
import com.swp.online_quizz.Repository.QuestionAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionAttemptsService implements IQuestionAttemptsService {
    @Autowired
    QuestionAttemptsRepository questionAttemptsRepository;

    @Override
    public List<QuestionAttempt> findByAttemptID(Integer attemptID) {
        return this.questionAttemptsRepository.findByAttemptID(attemptID);
    }
}
