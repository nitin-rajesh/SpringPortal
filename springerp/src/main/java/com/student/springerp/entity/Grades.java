package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "grades")
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade_letter", nullable = false)
    private String gradeLetter;

    @Column(name = "grade_point", nullable = false)
    private Double gradePoint;

    @Column(name = "comments")
    private String comments;

}

