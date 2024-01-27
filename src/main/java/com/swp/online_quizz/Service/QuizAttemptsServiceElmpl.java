package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.QuizAttempts;
import com.swp.online_quizz.Repository.QuizAttemptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<QuizAttempts> findQuizAttemptsByQuizID(Integer QuizID) {
        return this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizID);
    }

    @Override
    public Page<QuizAttempts> findQuizAttemptsByQuizID(Integer QuizzID, Integer pageNo) {
        List<QuizAttempts> quizAttempts = this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizzID);
        Pageable pageable = PageRequest.of(pageNo - 1, 5);
        Integer start = (int) pageable.getOffset();
        Integer end = ( start +pageable.getPageSize()) > quizAttempts.size() ? quizAttempts.size() : ( start +pageable.getPageSize());
        quizAttempts  = quizAttempts.subList(start,end);
        return new PageImpl<QuizAttempts>(quizAttempts,pageable,this.quizAttemptsRepository.findQuizAttemptsByQuizID(QuizzID).size());
    }

    @Override
    public List<QuizAttempts> searchUseByName(String useName,Integer quizzID) {
        return this.quizAttemptsRepository.searchUseByName(useName,quizzID);
    }

    @Override
    public Page<QuizAttempts> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,5);
        return this.quizAttemptsRepository.findAll(pageable);
    }

    @Override
    public Page<QuizAttempts> searchUseByName(String useName,Integer QuizzID, Integer pageNo) {
        List<QuizAttempts> list = this.searchUseByName(useName,QuizzID);
        Pageable pageable = PageRequest.of(pageNo-1,5);
        Integer start = (int) pageable.getOffset();
        Integer end = ( start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
        list  = list.subList(start,end);
        return new PageImpl<QuizAttempts>(list,pageable,this.searchUseByName(useName,QuizzID).size());
    }
}
