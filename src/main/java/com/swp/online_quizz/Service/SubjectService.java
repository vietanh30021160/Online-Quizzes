package com.swp.online_quizz.Service;

import java.util.*;

import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Repository.ClassQuizzRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.SubjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService {
    @Autowired
    private final SubjectRepository subrepository;
    @Autowired
    private final ClassesRepository classesRepository;
    @Autowired
    private final ClassQuizzRepository classQuizzRepository;
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
        } catch (Exception e) {
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
            Subject subject = new Subject(subjectName, "");
            return createSubject(subject);
        }
    }

    @Override
    @Transactional
    public Optional<Subject> updateSubjectBySubjectName(String subjectName, String newSubjectName,
            String newDescription) {
        subrepository.updateSubjectBySubjectName(subjectName, newSubjectName, newDescription);

        return subrepository.findBySubjectName(newSubjectName);
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

    @Override
    public Set<Subject> getSubjectsByClasses(List<Classes> classes) {
        List<Integer> classIds = classesRepository.findClassIdsByClasses(classes);
        Set<Subject> subjects = new HashSet<>();
        for (Integer classId : classIds) {
            List<Integer> quizIds = classQuizzRepository.findQuizIdsByClassId(classId);
            List<Integer> subjectIds = quizRepository.findSubjectIdsByQuizIds(quizIds);
            for (Integer subjectId : subjectIds) {
                Optional<Subject> subject = subrepository.findById(subjectId);
                subject.ifPresent(subjects::add);
            }
        }
        return subjects;
    }
}
