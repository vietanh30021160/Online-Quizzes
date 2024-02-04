package com.swp.online_quizz.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Subjects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @Column(name = "SubjectID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    @Column(name = "SubjectName", nullable = false, length = 100)
    private String subjectName;

    @Lob
    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "subject")
    @JsonManagedReference
    private List<Quiz> listQuizzs;

    public Subject(String subjectName, String description) {
        this.subjectName = subjectName;
        this.description = description;
    }
}
