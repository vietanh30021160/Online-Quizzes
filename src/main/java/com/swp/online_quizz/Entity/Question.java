package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Questions")
public class Question {
    @Id
    @Column(name = "QuestionID", nullable = false)
    private Integer questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    private Quiz quiz;

    @Lob
    @Column(name = "QuestionContent")
    private String questionContent;

    @Column(name = "QuestionType", length = 20)
    private String questionType;

    @Column(name = "ImageURL")
    private String imageURL;

    @Column(name = "VideoURL")
    private String videoURL;

}