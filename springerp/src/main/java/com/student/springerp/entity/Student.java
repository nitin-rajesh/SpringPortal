package com.student.springerp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="student")
public class Student {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @Column(name = "roll_number", nullable = false, unique = true)
    private String rollNumber;


    @Getter
    @Setter
    @Column(name = "last_name")
    private String lastName;

    @Getter
    @Setter
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(name = "photo_path")
    private String photoPath;

    @Getter
    @Setter
    @Column(name = "cgpa")
    private Double cgpa;

    @Setter
    @Column(name = "total_creds")
    private Integer totalCreds;

    @Getter
    @Setter
    @Column(name = "grad_year")
    private Integer gradYear;

    @Getter
    @Setter
    @Column(name="password", nullable = false)
    private String password;

}
