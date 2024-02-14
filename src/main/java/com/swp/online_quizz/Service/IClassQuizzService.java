package com.swp.online_quizz.Service;

import org.springframework.transaction.annotation.Transactional;

public interface IClassQuizzService {
    @Transactional
    void deleteClassQuizzByQuizId(Integer quizId);
}
