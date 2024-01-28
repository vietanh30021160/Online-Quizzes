package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Quiz;

@Service

public interface IQuizzesService {
    // lấy ra quizz
    public Quiz getOneQuizz(Integer quizId);

    public List<Quiz> getAllQuizzes();

}
