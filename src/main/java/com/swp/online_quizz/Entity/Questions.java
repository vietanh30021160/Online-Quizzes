package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    @Id
    @Column(name = "QuestionID", nullable = false)
    private Integer id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "QuizID")
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

}
