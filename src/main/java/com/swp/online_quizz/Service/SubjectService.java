package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Repository.SubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService {
    @Autowired
    private final SubjectsRepository subrepository;
    @Autowired
    private final QuizRepository quizRepository;
    @Override
    public List<Subject> getAll() {
        return subrepository.findAll();
    }

    @Override
    public boolean create(Subject subjects) {
        try {
    this.subrepository.save(subjects);
    return true;
        }catch (Exception e){
          e.printStackTrace();
        }
        return false;
    }

    @Override
    public Subject find(Integer subjectID) {
        return null;
    }

    @Override
    public Boolean update(Subject subjects) {
        return null;
    }

    @Override
    public Boolean delete(Integer subjectID) {
        return null;
    }
}
