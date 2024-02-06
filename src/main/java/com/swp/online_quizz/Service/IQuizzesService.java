package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IQuizzesService {
    List<Quiz> getAll();
    public Subject find(Integer quizId);
    public Quiz findByID(Integer quizID);

    public List<Quiz> searchQuizzes(String keyword);

    Page<Quiz> getAll(Integer pageNo);

    public Quiz getOneQuizz(Integer quizId);

    public List<Quiz> getAllQuizzes();

    Page<Quiz> searchAndFilterAndSubject(String keyword, Integer pageNo, Integer min, Integer max, String subject);
}



