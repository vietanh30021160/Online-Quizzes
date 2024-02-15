package com.swp.online_quizz.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.QuizProgress;
import com.swp.online_quizz.Repository.QuizProgressRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizProgressService implements IQuizProgressService {
    @Autowired
    QuizProgressRepository quizProgressRepository;

    @Override
    @Transactional
    public void deleteQuizProcessByQuizId(Integer quizId) {
        List<QuizProgress> progressList = quizProgressRepository.findByAttemptQuizQuizId(quizId);
        quizProgressRepository.deleteAll(progressList);
    }
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

    @Override
    public Boolean updateQuizProgress(Integer id, QuizProgress quizProgress) {
        try {
            QuizProgress uQuizProgress = quizProgressRepository.getReferenceById(id);
            uQuizProgress.setAnswer(quizProgress.getAnswer());
            uQuizProgress.setIsAnswered(quizProgress.getIsAnswered());
            this.quizProgressRepository.save(uQuizProgress);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
