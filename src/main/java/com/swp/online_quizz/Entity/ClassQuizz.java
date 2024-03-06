package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ClassQuizzes")
public class ClassQuizz {
    @EmbeddedId
    private ClassQuizzId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ClassID", nullable = false, insertable = false, updatable = false)
    private Classes classes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QuizID", nullable = false, insertable = false, updatable = false)
    private Quiz quiz;

}