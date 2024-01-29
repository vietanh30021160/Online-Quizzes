package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subjects;
import com.swp.online_quizz.Repository.SubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ISubjectService implements SubjectsService {
    @Autowired
    private final SubjectsRepository subrepository;
    @Override
    public List<Subjects> getAll() {
        return subrepository.findAll();
    }

    @Override
    public boolean create(Subjects subjects) {
        try {
    this.subrepository.save(subjects);
    return true;
        }catch (Exception e){
          e.printStackTrace();
        }
        return false;
    }

    @Override
    public Subjects find(Integer subjectID) {
        return null;
    }

    @Override
    public Boolean update(Subjects subjects) {
        return null;
    }

    @Override
    public Boolean delete(Integer subjectID) {
        return null;
    }
}
