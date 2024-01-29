package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface ISubjectService {
    public List<Subject> getAll();
    public boolean create(Subject subjects);
    public Subject find(Integer subjectID);
    public Boolean update(Subject subjects);
    public Boolean delete(Integer subjectID);

}
