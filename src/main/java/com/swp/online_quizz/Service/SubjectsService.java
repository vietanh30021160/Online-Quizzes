package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subjects;

import javax.security.auth.Subject;
import java.util.List;

public interface SubjectsService {
    public List<Subjects> getAll();
    public boolean create(Subjects subjects);
    public Subjects find(Integer subjectID);
    public Boolean update(Subjects subjects);
    public Boolean delete(Integer subjectID);
}
