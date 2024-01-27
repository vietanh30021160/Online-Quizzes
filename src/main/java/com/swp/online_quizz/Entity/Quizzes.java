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
public class Quizzes {



    @Id
    @Column(name = "QuizID", nullable = false)
    private Integer quizId;


    @OneToMany(mappedBy = "quiz")
    private List<QuizAttempts> quizAttempts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name
            = "TeacherID")
    private Users teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubjectID")
    private Subjects subject;

    @Column(name = "QuizName", length = 100)
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

}
