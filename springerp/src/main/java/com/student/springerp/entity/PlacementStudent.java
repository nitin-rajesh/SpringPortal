package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "placement_student")
public class PlacementStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "placement_id", nullable = false)
    private Placement placement;  // Foreign key to Placement entity

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;  // Foreign key to Student entity

    @Lob
    @Column(name = "cv_application")
    private byte[] cvApplication;  // Medium blob to store CV application

    @Column(name = "about")
    private String about;  // Description or additional info

    @Column(name = "acceptance")
    private Boolean acceptance;  // Boolean to track acceptance (true/false)

    @Column(name = "comments")
    private String comments;  // Comments about the application

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;  // Date of the application
}

