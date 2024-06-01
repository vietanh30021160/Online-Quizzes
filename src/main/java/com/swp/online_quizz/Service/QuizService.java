package com.swp.online_quizz.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.online_quizz.Entity.ClassQuizz;
import com.swp.online_quizz.Entity.Classes;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.AnswersRepository;
import com.swp.online_quizz.Repository.ClassEnrollmentRepository;
import com.swp.online_quizz.Repository.ClassQuizzRepository;
import com.swp.online_quizz.Repository.ClassesRepository;
import com.swp.online_quizz.Repository.QuestionsRepository;
import com.swp.online_quizz.Repository.QuizRepository;
import com.swp.online_quizz.Repository.QuizRepositoryCustom;
import com.swp.online_quizz.Repository.QuizRepositoryCustomImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class QuizService implements IQuizzesService {
    @PersistenceContext
    private EntityManager entityManager;
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
    private ClassEnrollmentRepository classEnrollmentRepository;
    @Autowired
    private ClassQuizzRepository classQuizzRepository;
    @Autowired
    private QuizRepositoryCustom quizRepositoryCustom;
    @Autowired
    private QuizRepositoryCustomImpl quizRepositoryCustomImpl;
    @Autowired
    private ClassesRepository classesRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersRepository answersRepository;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public List<Quiz> getQuizByUserId(Integer userId) {
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
        Pageable pageable = PageRequest.of(pageNo - 1, 3);
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
    public Quiz getOneQuiz(Integer quizId) {
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
                            "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("subjectName")),
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


    @Override
    public Page<Quiz> searchAndFilterAndSubjectAndQuizIds(String keyword, Integer pageNo, Integer min, Integer max,
            String subject, List<Integer> quizIds, String className) {
        Specification<Quiz> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("quizName")),
                            "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("subjectName")),
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

        if (!quizIds.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get("quizId").in(quizIds));
        }

        // Lọc bằng className nếu className được cung cấp
        if (className != null && !className.isEmpty()) {
            // Lấy danh sách classQuizzes dựa trên className
            List<ClassQuizz> classQuizzes = classQuizzRepository.findByClassesClassName(className);

            // Tạo danh sách các quizIds từ classQuizzes
            List<Integer> classQuizIds = new ArrayList<>();
            for (ClassQuizz classQuizz : classQuizzes) {
                classQuizIds.add(classQuizz.getQuiz().getQuizId());
            }

            // Thêm điều kiện lọc theo classQuizIds
            spec = spec.and((root, query, criteriaBuilder) -> root.get("quizId").in(classQuizIds));
        }

        // Lấy tất cả kết quả từ filteredQuiz
        Page<Quiz> filteredQuiz = quizRepository.findAll(spec, PageRequest.of(0, Integer.MAX_VALUE));
        if (quizIds.isEmpty()) {
            filteredQuiz = Page.empty();
        }
        return filteredQuiz;
        // Lấy tất cả kết quả từ quizzesNotInClasses
//        Page<Quiz> quizzesNotInClasses = quizRepositoryCustom.findQuizzesNotInAnyClass(spec, PageRequest.of(0, Integer.MAX_VALUE));
//
//        // Kết hợp các kết quả từ filteredQuiz và quizzesNotInClasses thành một danh sách duy nhất
//        List<Quiz> combinedList = new ArrayList<>();
//        combinedList.addAll(filteredQuiz.getContent());
//        combinedList.addAll(quizzesNotInClasses.getContent());
//
//        // Tạo một Page mới từ danh sách kết quả kết hợp và trả về
//        int pageSize = 3; // Kích thước trang mong muốn
//        int totalSize = combinedList.size();
//        int startIndex = (pageNo - 1) * pageSize;
//        int endIndex = Math.min(startIndex + pageSize, totalSize);
//        List<Quiz> pageContent = combinedList.subList(startIndex, endIndex);
//
//        return new PageImpl<>(pageContent, PageRequest.of(pageNo - 1, pageSize), totalSize);
    }

    @Override
    public Page<Quiz> searchAndFilterAndSubjectForQuizzesNoClass(String keyword, Integer pageNo, Integer min,
            Integer max, String subject, String className) {
        Specification<Quiz> spec = Specification.where(null);

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("quizName")),
                            "%" + keyword.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("subject").get("subjectName")),
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
        // Lọc bằng className nếu className được cung cấp
        if (className != null && !className.isEmpty()) {
            // Lấy danh sách classQuizzes dựa trên className
            List<ClassQuizz> classQuizzes = classQuizzRepository.findByClassesClassName(className);

            // Tạo danh sách các quizIds từ classQuizzes
            List<Integer> classQuizIds = new ArrayList<>();
            for (ClassQuizz classQuizz : classQuizzes) {
                classQuizIds.add(classQuizz.getQuiz().getQuizId());
            }

            // Thêm điều kiện lọc theo classQuizIds
            spec = spec.and((root, query, criteriaBuilder) -> root.get("quizId").in(classQuizIds));
        }

        Pageable pageable = PageRequest.of(pageNo - 1, 3);
        Page<Quiz> quizzesNotInClasses = quizRepositoryCustom.findQuizzesNotInAnyClass(spec, pageable);


        return quizzesNotInClasses;

    }

    @Override
    public Page<Quiz> CombineQuizzes(String keyword, Integer pageNo, Integer min, Integer max, String subject,
            List<Integer> quizIds, String className) {
        Page<Quiz> filteredQuiz = searchAndFilterAndSubjectAndQuizIds(keyword, pageNo, min, max, subject, quizIds,
                className);
        Page<Quiz> quizzesNotInClasses = searchAndFilterAndSubjectForQuizzesNoClass(keyword, pageNo, min, max, subject,
                className);
        // Kết hợp các kết quả từ filteredQuiz và quizzesNotInClasses thành một danh
        // sách duy nhất
        List<Quiz> combinedList = new ArrayList<>();
        combinedList.addAll(filteredQuiz.getContent());
        combinedList.addAll(quizzesNotInClasses.getContent());

        // Tạo một Page mới từ danh sách kết quả kết hợp và trả về
        int pageSize = 3; // Kích thước trang mong muốn
        int totalSize = combinedList.size();
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalSize);
        List<Quiz> pageContent = new ArrayList<>();
        if (startIndex <= endIndex) {
            pageContent = combinedList.subList(startIndex, endIndex);
        }
        return new PageImpl<>(pageContent, PageRequest.of(pageNo - 1, pageSize), totalSize);
    }

    @Override
    public boolean checkUserAndQuiz(List<Classes> listClassesInUser, Integer quizId) {

        // Kiểm tra xem quizId có thuộc về bất kỳ lớp học nào không
        List<ClassQuizz> classQuizzess = classQuizzRepository.findByQuizQuizId(quizId);
        if (classQuizzess.isEmpty()) {
            // Nếu quizId không thuộc về bất kỳ lớp học nào, trả về true
            return true;
        }
        // Duyệt qua tất cả các lớp học
        for (Classes classItem : listClassesInUser) {
            // Lấy danh sách tất cả các bài kiểm tra trong lớp
            Set<ClassQuizz> classQuizzes = classItem.getClassQuizzes();

            // Duyệt qua tất cả các bài kiểm tra trong lớp
            for (ClassQuizz classQuizz : classQuizzes) {
                // Kiểm tra xem bài kiểm tra có phải là bài kiểm tra cần kiểm tra hay không
                if (classQuizz.getQuiz().getQuizId().equals(quizId)) {
                    // Nếu có, trả về true
                    return true;

                }
            }
        }
        // Nếu không tìm thấy bài kiểm tra trong bất kỳ lớp nào, trả về false
        return false;
    }
}
