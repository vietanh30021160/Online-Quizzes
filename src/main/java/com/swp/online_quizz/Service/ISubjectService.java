package com.swp.online_quizz.Service;

import java.util.List;

import com.swp.online_quizz.Entity.Subject;

public interface ISubjectService {
    public List<Subject> getAll();

    public boolean create(Subject subjects);

    public Subject find(Integer subjectID);

    public Boolean update(Subject subjects);

    public Boolean delete(Integer subjectID);

}
