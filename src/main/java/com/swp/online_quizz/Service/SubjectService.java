package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService {
    @Autowired
    private final SubjectRepository subrepository;
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
    public Subject getSubjectByName(String subjectName) {
        return subrepository.findBySubjectName(subjectName)
                .orElse(null);
    }
    @Override
    public Subject createSubject(Subject subject) {
        return subrepository.save(subject);
    }
    @Override
    public Subject createOrUpdateSubject(String subjectName) {

        Subject existingSubject = getSubjectByName(subjectName);

        if (existingSubject != null) {
            return existingSubject;
        } else {
            Subject subject = new Subject(subjectName,"");
            return createSubject(subject);
        }
    }

    @Override
    @Transactional
    public Optional<Subject> updateSubjectBySubjectName(String subjectName, String newSubjectName, String newDescription) {
        subrepository.updateSubjectBySubjectName(subjectName, newSubjectName, newDescription);


        return subrepository.findBySubjectName(newSubjectName);
    }


    @Override
    public Boolean delete(Integer subjectID) {
        return null;
    }
}
