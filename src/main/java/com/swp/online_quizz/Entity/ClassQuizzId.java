package com.swp.online_quizz.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ClassQuizzId implements Serializable {
    private static final long serialVersionUID = 851785899005299916L;
    @Column(name = "ClassID", nullable = false)
    private Integer classID;

    @Column(name = "QuizID", nullable = false)
    private Integer quizID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClassQuizzId entity = (ClassQuizzId) o;
        return Objects.equals(this.classID, entity.classID) &&
                Objects.equals(this.quizID, entity.quizID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID, quizID);
    }

}