package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Repository.ClassQuizzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Override
    public List<Integer> getQuizIdsByClassIds(List<Integer> classIds) {

        List<ClassQuizz> classQuizzes = classQuizzRepository.findByClassesClassIdIn(classIds);
        List<Integer> quizIds = new ArrayList<>();
        for (ClassQuizz classQuizz : classQuizzes) {
            quizIds.add(classQuizz.getQuiz().getQuizId());
        }
        return quizIds;
    }
}

