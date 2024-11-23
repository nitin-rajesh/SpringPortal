package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "students", uniqueConstraints = {
        @UniqueConstraint(columnNames = "roll_number"),
        @UniqueConstraint(columnNames = "email")
})
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "cgpa")
    private Double cgpa;

    @Column(name = "graduation_year")
    private Integer graduationYear;

    @ManyToOne
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;  // Foreign key to Domain entity

    @ManyToOne
    @JoinColumn(name = "specialisation_id", nullable = false)
    private Specialisation specialisation;  // Foreign key to Specialisation entity

    @ManyToOne
    @JoinColumn(name = "placement_id")
    private Placement placement;  // Foreign key to Placement entity (nullable)
}

