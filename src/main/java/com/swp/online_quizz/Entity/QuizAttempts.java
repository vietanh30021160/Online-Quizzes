package com.swp.online_quizz.Entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.*;
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
    @Column(name = "AttemptID", nullable = false)
    private Integer attemptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    private Quizzes quiz;

    @Column(name = "StartTime")
    private LocalDateTime startTime;

    @Column(name = "EndTime")
    private LocalDateTime endTime;


    @Column(name = "Marks")
    private Integer marks;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrentQuestionID")
    private Questions currentQuestion;

    public long getMinutesDifference(){
        return ChronoUnit.MINUTES.between(startTime, endTime);
    }
}
