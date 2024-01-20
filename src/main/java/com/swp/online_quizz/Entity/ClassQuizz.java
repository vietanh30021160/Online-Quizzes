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

    @MapsId("classID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ClassID", nullable = false)
    private Class classID;

    @MapsId("quizID")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "QuizID", nullable = false)
    private Quiz quiz;

}