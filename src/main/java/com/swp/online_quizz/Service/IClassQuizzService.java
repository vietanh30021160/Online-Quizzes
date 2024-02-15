package com.swp.online_quizz.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IClassQuizzService {
    @Transactional
    void deleteClassQuizzByQuizId(Integer quizId);

    List<Integer> getQuizIdsByClassIds(List<Integer> classIds);
}
