package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizAttemptsServiceElmpl implements QuizAttemptsService{

    @Autowired
    private QuizAttemptsRepository quizAttemptsRepository;


    @Override
    public List<QuizAttempts> getAll() {
        return this.quizAttemptsRepository.findAll();
    }

    @Override
    public Boolean create(QuizAttempts quizAttempts) {
        try {
            this.quizAttemptsRepository.save(quizAttempts);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public QuizAttempts findById(Integer AttemptsID) {
        return null;
    }

    @Override
    public Boolean update(QuizAttempts quizAttempts) {
        return null;
    }

    @Override
    public Boolean delete(Integer AttemptsID) {
        return null;
    }
}
