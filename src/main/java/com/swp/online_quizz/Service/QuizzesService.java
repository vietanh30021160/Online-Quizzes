package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quizzes;
import com.swp.online_quizz.Entity.Subjects;

import java.util.List;

public interface QuizzesService {
    List<Quizzes> getAll();
    public boolean create(Quizzes quizzes);
    public Subjects find(Integer quizId);
    public Boolean update(Quizzes quizzes);
    public Boolean delete(Integer quizId);
}
