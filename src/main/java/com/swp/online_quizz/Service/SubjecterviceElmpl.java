package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SubjecterviceElmpl implements SubjectService {
    @Autowired
    private final SubjectRepository subrepository;
    @Override
    public List<Subject> getAll() {
        return subrepository.findAll();
    }

    @Override
    public boolean create(Subject Subject) {
        try {
    this.subrepository.save(Subject);
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
    public Boolean update(Subject Subject) {
        return null;
    }

    @Override
    public Boolean delete(Integer subjectID) {
        return null;
    }
}
