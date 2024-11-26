package com.student.springerp.crudops;

import com.student.springerp.entity.Student;
import com.student.springerp.entity.StudentCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface StudentCourseRepo extends JpaRepository<StudentCourses, Long> {

    @Query("SELECT AVG(sc.grade.gradePoint) " +
            "FROM StudentCourses sc " +
            "JOIN Grades g ON g = sc.grade " +
            "JOIN Student s WHERE s.id = :id")
    List<Double> findTotalGradesForCourses(@Param("id") Long studentId);


}
