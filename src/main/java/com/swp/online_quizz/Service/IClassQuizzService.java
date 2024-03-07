package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Quiz;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClassQuizzService {
    @Transactional
    void deleteClassQuizzByQuizId(Integer quizId);

    List<Integer> getQuizIdsByClassIds(List<Integer> classIds);
    List<ClassQuizz> getQuizByClassId(Integer classID);
    Boolean isAddQuizInClass(Classes classes , Quiz quiz);
}
