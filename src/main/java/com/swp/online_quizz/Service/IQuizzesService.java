package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Quizzes;

@Service

public interface IQuizzesService {
    // lấy ra quizz
    public Quizzes getOneQuizz(Integer quizId);

    public List<Quizzes> getAllQuizzes();
}
