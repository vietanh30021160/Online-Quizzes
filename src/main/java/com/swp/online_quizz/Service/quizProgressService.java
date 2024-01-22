package com.swp.online_quizz.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Repository.QuizProgressRepository;

@Service
public class QuizProgressService implements IQuizProgressService {
    @Autowired
    QuizProgressRepository quizProgressRepository;

    @Override
    public Boolean createQuizProgress(QuizProgress quizProgress) {
        try {
            this.quizProgressRepository.save(quizProgress);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
