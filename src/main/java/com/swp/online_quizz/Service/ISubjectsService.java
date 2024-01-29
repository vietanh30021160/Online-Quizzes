package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface ISubjectsService {
    public List<Subject> getAll();
    public boolean create(Subject subjects);
    public Subject find(Integer subjectID);
    public Boolean update(Subject subjects);
    public Boolean delete(Integer subjectID);
}
