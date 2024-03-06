package com.swp.online_quizz.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Classes")
public class Classes {
    @Id
    @Column(name = "ClassID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TeacherID")
    private User teacher;

    @Column(name = "ClassName", length = 100)
    private String className;

    @Column(name = "ClassCode", length = 100)
    private String classCode;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ClassEnrollment> listEnrollment = new ArrayList<>();

    @OneToMany(mappedBy = "classes",cascade = CascadeType.ALL)
    Set<ClassQuizz> idClassQuizzs;
}