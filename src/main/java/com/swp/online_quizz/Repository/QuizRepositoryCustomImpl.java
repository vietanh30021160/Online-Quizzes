package com.swp.online_quizz.Repository;

import com.swp.online_quizz.Entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuizRepositoryCustomImpl implements QuizRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public QuizRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Quiz> findQuizzesNotInAnyClass(Specification<Quiz> spec, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteriaQuery = criteriaBuilder.createQuery(Quiz.class);
        Root<Quiz> root = criteriaQuery.from(Quiz.class);

        // Tạo một điều kiện để lọc các bài quiz không thuộc bất kỳ lớp nào
        Predicate notInAnyClassPredicate = criteriaBuilder.isEmpty(root.get("classQuizzes"));

        // Thêm điều kiện từ Specification vào truy vấn
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, criteriaQuery, criteriaBuilder);
            if (predicate != null) {
                notInAnyClassPredicate = criteriaBuilder.and(notInAnyClassPredicate, predicate);
            }
        }

        criteriaQuery.where(notInAnyClassPredicate);

        // Thực hiện truy vấn để lấy danh sách các bài quiz không thuộc bất kỳ lớp nào
        TypedQuery<Quiz> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Quiz> quizzes = query.getResultList();

        // Đếm tổng số lượng bài quiz không thuộc bất kỳ lớp nào
        long total = countQuizzesNotInAnyClass(spec);

        return new PageImpl<>(quizzes, pageable, total);
    }

    private long countQuizzesNotInAnyClass(Specification<Quiz> spec) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Quiz> root = countQuery.from(Quiz.class);
        countQuery.select(criteriaBuilder.count(root));

        // Tạo một điều kiện để đếm số lượng bài quiz không thuộc bất kỳ lớp nào
        Predicate notInAnyClassPredicate = criteriaBuilder.isEmpty(root.get("classQuizzes"));

        // Thêm điều kiện từ Specification vào truy vấn đếm
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, countQuery, criteriaBuilder);
            if (predicate != null) {
                notInAnyClassPredicate = criteriaBuilder.and(notInAnyClassPredicate, predicate);
            }
        }

        countQuery.where(notInAnyClassPredicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
