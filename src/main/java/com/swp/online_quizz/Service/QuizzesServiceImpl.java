package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Repository.QuizzesRepository;

@Service
public class QuizzesServiceImpl implements IQuizzesService {
    @Autowired
    private QuizzesRepository quizzesRepository;

    @Override
    public Quizzes getOneQuizz(Integer quizId) {
        return quizzesRepository.getReferenceById(quizId);
    }

    @Override
    public List<Quizzes> getAllQuizzes() {
        return quizzesRepository.findAll();
    }
}
