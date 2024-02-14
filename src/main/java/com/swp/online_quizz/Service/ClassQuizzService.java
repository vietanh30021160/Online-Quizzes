package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Repository.ClassQuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassQuizzService implements IClassQuizzService{
    @Autowired
    public ClassQuizzRepository classQuizzRepository;
    @Override
    public void deleteClassQuizzByQuizId(Integer quizId) {
        List<ClassQuizz> classQuizzes = classQuizzRepository.findByQuizQuizId(quizId);
        classQuizzRepository.deleteAll(classQuizzes);
    }
}
