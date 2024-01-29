package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface IQuizzesService {
    List<Quiz> getAll();
    public boolean create(Quiz quizzes);
    public Subject find(Integer quizId);
    public Quiz findByID(Integer quizID);
    public Boolean update(Quiz quizzes);
    public Boolean delete(Integer quizId);
}
