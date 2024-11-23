package com.student.springerp.crudops;

import com.student.springerp.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradesRepo extends JpaRepository<Grades, Long> {
}
