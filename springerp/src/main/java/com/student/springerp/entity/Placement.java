package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "placements")
public class Placement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    private Organisation organisation;  // Foreign key to Organisation entity

    @Column(name = "profile", nullable = false)
    private String profile;

    @Column(name = "description")
    private String description;

    @Column(name = "intake")
    private Integer intake;

    @Column(name = "min_grade")
    private Double minGrade;
}

