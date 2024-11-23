package com.student.springerp.crudops;

import com.student.springerp.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepo extends JpaRepository<Students, Long> {

    Students findByEmail(String email);
}
