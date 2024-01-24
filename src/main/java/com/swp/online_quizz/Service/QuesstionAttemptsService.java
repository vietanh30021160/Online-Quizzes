package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempts;
import com.swp.online_quizz.Repository.QuestionAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuesstionAttemptsService implements IQuesstionAttemptsService{
    @Autowired
    QuestionAttemptsRepository questionAttemptsRepository;
    @Override
    public Boolean createQuesstionAttempts(QuestionAttempts questionAttempts) {
        try {
            this.questionAttemptsRepository.save(questionAttempts);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
