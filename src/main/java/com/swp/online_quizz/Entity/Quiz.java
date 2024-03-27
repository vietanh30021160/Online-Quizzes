package com.swp.online_quizz.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @Column(name = "QuizID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quizId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeacherID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Subject subject;

    private String subjectName;

    @Column(name = "QuizName", length = 100)
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Question> listQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "quiz")
    @JsonManagedReference
    private List<QuizAttempt> listQuizAttemps;

    @OneToMany(mappedBy = "quiz") // Thêm mối quan hệ mới ở đây
    @JsonManagedReference
    private List<ClassQuizz> classQuizzes; // Thêm List<ClassQuizz>

    // @OneToMany(mappedBy = "quiz")
    // @JsonManagedReference
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    // private Set<QuizAttempt> listQuizAttemps;
    @OneToMany(mappedBy = "quiz")
    private List<QuizAttempt> quizAttempts;

    public Quiz(User teacher, Subject subjectName, String quizName, Integer timeLimit, Boolean isCompleted) {
        this.teacher = teacher;
        this.subject = subjectName;
        this.quizName = quizName;
        this.timeLimit = timeLimit;
        this.isCompleted = isCompleted;
    }

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    Set<ClassQuizz> idClassQuizzs;
}
