package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student_courses")
public class StudentCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;  // Foreign key to Student entity

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;  // Foreign key to Course entity

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grades grade;  // Foreign key to Grade entity (nullable)

    @Column(name = "comments")
    private String comments;
}

