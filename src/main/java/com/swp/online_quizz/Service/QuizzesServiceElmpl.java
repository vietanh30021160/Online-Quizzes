package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Subjects;
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
    public List<Quizzes> getAll() {
        return quizzesRepository.findAll();
    }

    @Override
    public boolean create(Quizzes quizzes) {
        return false;
    }

    @Override
    public Subjects find(Integer quizId) {
        return null;
    }

    @Override
    public Quizzes findByID(Integer quizID) {
        return null;
    }

    @Override
    public Boolean update(Quizzes quizzes) {
        return null;
    }

    @Override
    public Boolean delete(Integer quizId) {
        return null;
    }

    @Override
    public Quizzes QuizzAndSubjectBySubjectID(Integer SubjectID) {
        return quizzesRepository.findQuizzesBySubjectID(SubjectID);
    }

}
