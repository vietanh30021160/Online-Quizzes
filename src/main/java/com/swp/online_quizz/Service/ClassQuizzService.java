package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.ClassQuizzId;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Repository.ClassQuizzRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassQuizzService implements IClassQuizzService {
    @Autowired
    public ClassQuizzRepository classQuizzRepository;
    @Autowired
    private EntityManager entityManager;

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

    @Override
    public List<ClassQuizz> getQuizByClassId(Integer classID) {
        return this.classQuizzRepository.findQuizByClassID(classID);
    }

    @Override
    public Boolean isAddQuizInClass(Classes classes, Quiz quiz) {
        try {
            ClassQuizz classQuizz = new ClassQuizz();
            ClassQuizzId id = new ClassQuizzId();

            id.setClassId(classes.getClassId());
            id.setQuizId(quiz.getQuizId());
            classQuizz.setId(id);
            this.classQuizzRepository.save(classQuizz);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

