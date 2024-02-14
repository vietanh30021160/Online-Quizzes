package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.QuizAttempt;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class QuizAttemptsService implements IQuizAttemptsService {
    @Autowired
    private QuizAttemptsRepository quizAttemptsRepository;
    @Override
    @Transactional
    public void deleteQuizAttemptsByQuizId(Integer quizId) {
        List<QuizAttempt> attempts = quizAttemptsRepository.findByQuizQuizId(quizId);
        quizAttemptsRepository.deleteAll(attempts);
    }
    @Override
    public List<QuizAttempt> getQuizAttemptsByQuizId(Integer quizId) {
        return quizAttemptsRepository.findByQuizQuizId(quizId);
    }


    @Override
    public List<QuizAttempt> getAll() {
        return this.quizAttemptsRepository.findAll();
    }

    @Override
    public Boolean create(QuizAttempt quizAttempts) {
        try {
            this.quizAttemptsRepository.save(quizAttempts);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public QuizAttempt findById(Integer AttemptsID) {
        return this.quizAttemptsRepository.findById(AttemptsID).orElse(null);
    }


    @Override
    public Boolean update(QuizAttempt quizAttempts) {
        return null;
    }

    @Override
    public Boolean delete(Integer AttemptsID) {
        return null;
    }

    @Override
    public List<QuizAttempt> findQuizAttemptsByQuizID(Integer QuizID , Sort sort) {
        return this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizID,sort);
    }

    @Override
    public Page<QuizAttempt> findQuizAttemptsByQuizID(Integer QuizzID, Integer pageNo,Sort sort) {
        List<QuizAttempt> quizAttempts = this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizzID,sort);
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = ( start +pageable.getPageSize()) > quizAttempts.size() ? quizAttempts.size() : ( start +pageable.getPageSize());
        quizAttempts  = quizAttempts.subList(start,end);
        return new PageImpl<QuizAttempt>(quizAttempts,pageable,this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizzID,sort).size());
    }

    @Override
    public List<QuizAttempt> searchUseByName(String username, Integer quizzID) {
        return this.quizAttemptsRepository.searchUseByName(username,quizzID);
    }
    @Override
    public Page<QuizAttempt> searchUseByName(String username, Integer QuizzID, Integer pageNo) {
        List<QuizAttempt> quizAttempts = quizAttemptsRepository.searchUseByName(username,QuizzID);
        Pageable pageable = PageRequest.of(pageNo-1,5);
        Integer start = (int) pageable.getOffset();
        Integer end = ( start + pageable.getPageSize()) > quizAttempts.size() ? quizAttempts.size() : ( start + pageable.getPageSize());
        quizAttempts  = quizAttempts.subList(start,end);
        return new PageImpl<QuizAttempt>(quizAttempts,pageable,quizAttemptsRepository.searchUseByName(username,QuizzID).size());
    }

    @Override
    public Page<QuizAttempt> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,5);
        return this.quizAttemptsRepository.findAll(pageable);
    }
    @Override
    public QuizAttempt getQuizAttempts(Integer quizAttemptID) {
        return quizAttemptsRepository.getReferenceById(quizAttemptID);
    }

    @Override
    public boolean createQuizzAttempt(QuizAttempt attempt) {
        try {
            this.quizAttemptsRepository.save(attempt);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<QuizAttempt> getAttemptByUserIdAndQuizzId(Quiz quiz, User user) {
        return quizAttemptsRepository.findByQuizIdAndUserId(quiz.getQuizId(), user.getUserId());
    }

    @Override
    public List<QuizAttempt> findByQuizIdAndUserIdAndStartTime(Quiz quiz, User user, Timestamp startTime) {
        return quizAttemptsRepository.findByQuizIdAndUserIdAndStartTime(quiz.getQuizId(), user.getUserId(), startTime);
    }

    @Override
    public QuizAttempt updateAttempts(Integer id, QuizAttempt attempt) {
        QuizAttempt existingAttempt = quizAttemptsRepository.getReferenceById(id);
        if (existingAttempt != null) {
            existingAttempt.setEndTime(attempt.getEndTime());
            existingAttempt.setIsCompleted(attempt.getIsCompleted());
            existingAttempt.setMarks(attempt.getMarks());
            return quizAttemptsRepository.save(existingAttempt);
        } else {
            throw new EntityNotFoundException("Attempt not found with id: " + id);
        }
    }


}
