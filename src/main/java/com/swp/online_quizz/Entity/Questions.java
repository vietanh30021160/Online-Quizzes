package com.swp.online_quizz.Entity;

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
@Table(name = "Questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    @Id
    @Column(name = "QuestionID")
    private Integer questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QuizID")
    @JsonBackReference
    private Quizzes quiz;

    @Column(name = "QuestionContent")
    private String questionContent;

    @Column(name = "QuestionType")
    private String questionType;

    @Column(name = "ImageURL")
    private String imageUrl;

    @Column(name = "VideoURL")
    private String videoUrl;

    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    private Set<Answers> listAnswer;

    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    private Set<QuestionAttempts> listQuestionAttempts;
}
