package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class QuizProgress {
    @Id
    @Column(name = "ProgressID", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttemptID")
    private QuizAttempt attemptID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuestionID")
    private Question questionID;

    @Column(name = "IsAnswered")
    private Boolean isAnswered;

    @Column(name = "Answer")
    private String answer;

}