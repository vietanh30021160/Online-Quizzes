package com.swp.online_quizz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Quizzes {
    @Id
    @Column(name = "QuizID")
    private Integer quizId;

    @Column(name = "TeacherID")
    private Integer teacherId;

    @Column(name = "SubjectID")
    private Integer subjectId;

    @Column(name = "QuizName")
    private String quizName;

    @Column(name = "TimeLimit")
    private Integer timeLimit;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

}
