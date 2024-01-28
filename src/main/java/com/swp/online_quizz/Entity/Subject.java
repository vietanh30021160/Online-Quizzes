package com.swp.online_quizz.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Integer subjectId;

    @Column(name = "SubjectName")
    private String subjectName;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "subject")
    @JsonManagedReference
    private List<Quiz> listQuizzs;
}
