package com.swp.online_quizz.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuizAttempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAttempts {
    @Id
    @Column(name = "AttemptID")
    private Integer attemptId;

    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "QuizID")
    private Integer quizId;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;

    @Column(name = "Marks")
    private Integer marks;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @Column(name = "CurrentQuestionID")
    private Integer currentQuestionId;

}
