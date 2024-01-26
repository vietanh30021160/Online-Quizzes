package com.swp.online_quizz.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class Subjectervice implements ISubjectService {
    @Autowired
    private final SubjectRepository subrepository;
    @Autowired
    private final QuizRepository quizRepository;
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


    @Override
    public List<Quiz> getQuizzesBySubjectId(Integer subjectId) {
            return quizRepository.findBySubjectId(subjectId);
    }
}
