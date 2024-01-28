package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuizzesService {

    public List<Quiz> searchQuizzes(String keyword);
    Page<Quiz> getAll(Integer pageNo);
    public Page<Quiz> searchQuizzes(String keyword,Integer pageNo);
}
