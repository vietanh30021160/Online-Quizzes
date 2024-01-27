package com.swp.online_quizz.Entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttemptID")
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


    public QuizAttempts(Users user, Quizzes quiz, LocalDateTime startTime, LocalDateTime endTime, Integer marks,
            Boolean isCompleted, Questions currentQuestionId, List<Feedback> listFeedbacks,
                        List<QuestionAttempts> listQuestionAttempts, List<QuizProgress> listQuizzProgress) {
        this.user = user;
        this.quiz = quiz;
        this.startTime = startTime;
        this.endTime = endTime;
        this.marks = marks;
        this.isCompleted = isCompleted;
        this.currentQuestion = currentQuestionId;
        this.listFeedbacks = listFeedbacks;
        this.listQuestionAttempts = listQuestionAttempts;
        this.listQuizzProgress = listQuizzProgress;
    }

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<Feedback> listFeedbacks;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<QuestionAttempts> listQuestionAttempts;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<QuizProgress> listQuizzProgress;

}
