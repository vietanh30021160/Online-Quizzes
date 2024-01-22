package com.swp.online_quizz.Entity;

import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    @JsonBackReference
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    @JsonBackReference
    private Quizzes quiz;

    @Column(name = "StartTime")
    private Timestamp startTime;

    @Column(name = "EndTime")
    private Timestamp endTime;

    @Column(name = "Marks")
    private Integer marks;

    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @Column(name = "CurrentQuestionID")
    private Integer currentQuestionId;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private Set<Feedback> listFeedbacks;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private Set<QuestionAttempts> listQuestionAttempts;

    @OneToMany(mappedBy = "attempt")
    @JsonManagedReference
    private Set<QuizProgress> listQuizzProgress;
}
