package com.swp.online_quizz.Entity;

import java.sql.Timestamp;
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
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AttemptID")
    private Integer attemptId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    private Quiz quiz;

    @Column(name = "StartTime")
    private Timestamp startTime;

    @Column(name = "EndTime")
    private Timestamp endTime;


    @Column(name = "Marks")
    private Integer marks;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrentQuestionID")
    private Question currentQuestion;


    public String getMinutesDifference(){
        long millisecondsDifference = endTime.getTime() - startTime.getTime();
        long secondsDifference = millisecondsDifference / 1000;
        long minutesDifference = secondsDifference / 60;
        long remainingSeconds = secondsDifference % 60;

        return minutesDifference + " Ms " + remainingSeconds + " s";
    }


    public QuizAttempt(User user, Quiz quiz, Timestamp startTime, Timestamp endTime, Integer marks,
                       Boolean isCompleted, Question currentQuestionId, List<Feedback> listFeedbacks,
                       List<QuestionAttempt> listQuestionAttempts, List<QuizProgress> listQuizzProgress) {
        this.user = user;
        this.quiz = quiz;
        this.startTime = startTime;
        this.endTime = endTime;
        this.marks = marks;
        this.isCompleted = isCompleted;
        this.currentQuestion = currentQuestion;
        this.listFeedbacks = listFeedbacks;
        this.listQuestionAttempts = listQuestionAttempts;
        this.listQuizzProgress = listQuizzProgress;
    }

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<Feedback> listFeedbacks;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<QuestionAttempt> listQuestionAttempts;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private List<QuizProgress> listQuizzProgress;

}
