package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizzesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class QuizzesServiceElmpl implements QuizzesService{
    @Autowired
    private final QuizzesRepository quizzesRepository;
    @Override
    public List<Quiz> getAll() {
        return quizzesRepository.findAll();
    }

    @Override
    public boolean create(Quiz quizzes) {
        return false;
    }

    @Override
    public Subject find(Integer quizId) {
        return null;
    }

    @Override
    public Boolean update(Quiz quizzes) {
        return null;
    }

    @Override
    public Boolean delete(Integer quizId) {
        return null;
    }
}
