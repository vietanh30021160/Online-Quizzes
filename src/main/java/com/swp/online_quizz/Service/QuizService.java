package com.swp.online_quizz.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class QuizService implements IQuizzesService {
    @Autowired
    private final QuizRepository quizRepository;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public Subject find(Integer quizId) {
        return null;
    }

    @Override
    public Quiz findByID(Integer quizID) {
        return quizRepository.findById(quizID).orElse(null);
    }

    @Override
    public List<Quiz> searchQuizzes(String keyword) {
        return quizRepository.findByKeywordContainingIgnoreCase(keyword);
    }

    @Override
    public Page<Quiz> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 3);
        return this.quizRepository.findAll(pageable);
    }

    @Override
    public Quiz getOneQuizz(Integer quizId) {
        return quizRepository.getReferenceById(quizId);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Page<Quiz> searchAndFilterAndSubject(String keyword, Integer pageNo, Integer min, Integer max,
            String subject) {
        Specification<Quiz> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("quizName")),
                            "%" + keyword.toLowerCase() + "%")));
        }

        if (subject != null && !subject.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder
                    .equal(root.get("subject").get("subjectName"), subject));
        }

        if (min != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("timeLimit"), min));
        }

        if (max != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("timeLimit"), max));
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 3);

        return quizRepository.findAll(spec, pageable);
    }

}
