package com.student.springerp.crudops;

import com.student.springerp.entity.Student;
import com.student.springerp.entity.StudentCourses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepo extends JpaRepository<StudentCourses, Long> {
}
