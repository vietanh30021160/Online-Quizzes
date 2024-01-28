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
public class SubjectService implements ISubjectService {
    @Autowired
    private final SubjectRepository subrepository;
    @Autowired
    private final QuizRepository quizRepository;
    @Override
    public List<Subject> getAll() {
        return subrepository.findAll();
    }


}
