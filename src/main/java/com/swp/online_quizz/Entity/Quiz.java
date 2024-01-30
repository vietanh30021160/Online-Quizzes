package com.swp.online_quizz.Entity;

<<<<<<< HEAD
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
=======
>>>>>>> origin/TuanLQ_copy
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {



    @Id
    @Column(name = "QuizID", nullable = false)
    private Integer quizId;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name
            = "TeacherID")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
<<<<<<< HEAD
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
=======
>>>>>>> origin/TuanLQ_copy
    private Subject subject;

    @Column(name = "QuizName", length = 100)
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @OneToMany(mappedBy = "quiz")
    @JsonManagedReference
    private List<Question> listQuestions;

<<<<<<< HEAD
    @OneToMany(mappedBy = "quiz")
    @JsonManagedReference
    private List<QuizAttempt> listQuizAttemps;
=======
//    @OneToMany(mappedBy = "quiz")
//    @JsonManagedReference
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//    private Set<QuizAttempt> listQuizAttemps;
@OneToMany(mappedBy = "quiz")
private List<QuizAttempt> quizAttempts;
>>>>>>> origin/TuanLQ_copy
}
