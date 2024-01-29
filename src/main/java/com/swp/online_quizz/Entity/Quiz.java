package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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


    @OneToMany(mappedBy = "quiz")
    private List<QuizAttempt> quizAttempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name
            = "TeacherID")
    private User teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
    private Subject subject;

    @Column(name = "QuizName", length = 100)
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

}
