package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Repository.QuestionAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IQuestionAttemptsService implements QuestionAttemptsService{
    @Autowired
    public QuestionAttemptsRepository questionAttemptsRepository;
    @Override
    public List<QuestionAttempts> findByAttemptID(Integer attemptID) {
        return this.questionAttemptsRepository.findByAttemptID(attemptID);
    }
}
