package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @Column(name = "QuestionID", nullable = false)
    private Integer questionID;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "QuizID")
//    @JsonBackReference
//    private Quizzes quiz;

    @Lob
    @Column(name = "QuestionContent")
    private String questionContent;

    @Column(name = "QuestionType")
    private String questionType;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "VideoURL")
    private String videoUrl;

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList;
}
