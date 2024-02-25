package com.swp.online_quizz.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.AnswersRepository;
import com.swp.online_quizz.Repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.QuizRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class QuizService implements IQuizzesService {
    @Autowired
    private final QuizRepository quizRepository;
    @Autowired
    private IQuestionsService questionsService;
    @Autowired
    private IAnswerService iAnswerService;
    @Autowired
    @Lazy
    private ISubjectService iSubjectService;
    @Autowired
    private IUsersService iUsersService;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersRepository answersRepository;



    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }
    @Override
    public List<Quiz> getQuizByUserId(Integer userId){
        return quizRepository.findByTeacherUserId(userId);
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
    public Quiz findQuizById(Integer quizId) {
        return quizRepository.getReferenceById(quizId);
    }
    @Transactional
    @Override
    public boolean createQuiz1(Quiz quiz) {
        try {
            Subject existingSubject = iSubjectService.createOrUpdateSubject(quiz.getSubject().getSubjectName());
            User teacher = iUsersService.getUsersByID(quiz.getTeacher().getUserId());

            quiz.setSubject(existingSubject);
            quiz.setTeacher(teacher);
            quiz.setIsCompleted(false);

            quizRepository.save(quiz);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public Quiz getEmptyQuiz() {
        Quiz quiz = new Quiz();
        // Initialize other properties if needed
        return quiz;
    }

    @Transactional
    @Override
    public Boolean updateQuizByQuizId1(Integer id, Quiz quiz) {
        try {
            Quiz uQuiz = quizRepository.getReferenceById(id);
            uQuiz.setQuizName(quiz.getQuizName());
            uQuiz.setTimeLimit(quiz.getTimeLimit());
            this.quizRepository.save(uQuiz);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    public void deleteQuizById(Integer quizId) {
        Optional<Quiz> optionalQuiz = quizRepository.findByQuizId(quizId);
        optionalQuiz.ifPresent(quiz -> quizRepository.delete(quiz));
    }
    @Override
    public List<Quiz> searchQuizzes(String keyword) {
        return quizRepository.findByKeywordContainingIgnoreCase(keyword);
    }

    @Override
    public Page<Quiz> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo -1, 3);
        return this.quizRepository.findAll(pageable);
    }

    @Override
    public Page<Quiz> searchQuizzes(String keyword, Integer pageNo) {
        List list = this.searchQuizzes(keyword);
        Pageable pageable = PageRequest.of(pageNo - 1, 3);
        Integer start = (int) pageable.getOffset();
        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size()
                : pageable.getOffset() + pageable.getPageSize());
        list = list.subList(start, end);
        return new PageImpl<Quiz>(list, pageable, this.searchQuizzes(keyword).size());
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
    public Page<Quiz> searchAndFilterAndSubject(String keyword, Integer pageNo, Integer min, Integer max, String subject) {
        Specification<Quiz> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("quizName")),
                            "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("subjectName")),
                            "%" + keyword.toLowerCase() + "%")));
        }

        if (subject != null && !subject.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("subject").get("subjectName"), subject));
        }

        if (min != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("timeLimit"), min));
        }

        if (max != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("timeLimit"), max));
        }

        Pageable pageable = PageRequest.of(pageNo -1, 3);

        return quizRepository.findAll(spec, pageable);
    }
    @Override
    public Page<Quiz> searchAndFilterAndSubjectAndQuizIds(String keyword, Integer pageNo, Integer min, Integer max, String subject, List<Integer> quizIds) {
        Specification<Quiz> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("quizName")),
                            "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("subjectName")),
                            "%" + keyword.toLowerCase() + "%")));
        }

        if (subject != null && !subject.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("subject").get("subjectName"), subject));
        }

        if (min != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("timeLimit"), min));
        }

        if (max != null) {
            spec = spec.and(
                    (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("timeLimit"), max));
        }

        // Thêm điều kiện để lọc theo quizIds
        if (!quizIds.isEmpty() ) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get("quizId").in(quizIds));
        }
        // Nếu quizIds là null hoặc rỗng thì trả về một Page rỗng
        if (quizIds == null || quizIds.isEmpty()) {
            return Page.empty();
        }


        Pageable pageable = PageRequest.of(pageNo -1, 3);

        return quizRepository.findAll(spec, pageable);
    }
}
