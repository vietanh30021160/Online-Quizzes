package com.swp.online_quizz.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Subjects")
public class Subject {
    @Id
    @Column(name = "SubjectID", nullable = false)
    private Integer id;

    @Column(name = "SubjectName", nullable = false, length = 100)
    private String subjectName;

    @Lob
    @Column(name = "Description")
    private String description;

}