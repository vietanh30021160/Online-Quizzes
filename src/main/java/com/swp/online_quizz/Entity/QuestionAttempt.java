package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "QuestionAttempts")
public class QuestionAttempt {
    @EmbeddedId
    private QuestionAttemptId id;

    @MapsId("attemptID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "AttemptID", nullable = false)
    private QuizAttempt attemptID;

    @MapsId("questionID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "QuestionID", nullable = false)
    private Question questionID;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "IsCorrect")
    private Boolean isCorrect;

}