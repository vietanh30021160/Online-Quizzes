package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;

import java.util.List;

public interface SubjectService {
    public List<Subject> getAll();
    public boolean create(Subject Subject);
    public Subject find(Integer subjectID);
    public Boolean update(Subject Subject);
    public Boolean delete(Integer subjectID);
}
