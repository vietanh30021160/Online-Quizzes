package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface QuizzesService {
    List<Quiz> getAll();
    public boolean create(Quiz quizzes);
    public Subject find(Integer quizId);
    public Boolean update(Quiz quizzes);
    public Boolean delete(Integer quizId);
    public List<Quiz> searchQuizzes(String keyword);
}
