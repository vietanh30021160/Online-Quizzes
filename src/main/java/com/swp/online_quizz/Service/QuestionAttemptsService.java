package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuestionAttempt;
import com.swp.online_quizz.Repository.QuestionAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class QuestionAttemptsService implements IQuestionAttemptsService {
    @Autowired
    QuestionAttemptsRepository questionAttemptsRepository;

    @Override
    @Transactional
    public void deleteQuestionAttemptsByQuizId(Integer quizId) {
        List<QuestionAttempt> questionAttempts = questionAttemptsRepository.findByAttemptQuizQuizId(quizId);
        questionAttemptsRepository.deleteAll(questionAttempts);
    }
    @Override
    public Boolean createQuesstionAttempts(QuestionAttempt questionAttempts) {
        try {
            this.questionAttemptsRepository.save(questionAttempts);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean updateQuesstionAttempts(Integer id, QuestionAttempt questionAttempts) {
        try {
            QuestionAttempt uQuestionAttempts = questionAttemptsRepository.getReferenceById(id);
            uQuestionAttempts.setAnswer(questionAttempts.getAnswer());
            uQuestionAttempts.setIsAnswered(questionAttempts.getIsAnswered());
            uQuestionAttempts.setIsCorrect(questionAttempts.getIsCorrect());
            this.questionAttemptsRepository.save(uQuestionAttempts);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<QuestionAttempt> findByAttemptID(Integer attemptID) {
        return this.questionAttemptsRepository.findByAttemptID(attemptID);
    }
}
