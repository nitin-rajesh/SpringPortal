package com.student.springerp.crudops;

import com.student.springerp.entity.Student;
import com.student.springerp.entity.StudentFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentFilterRepo extends JpaRepository<Student, Long> {
    @Query("SELECT sf FROM StudentFilter sf " +
            "JOIN FETCH sf.student s " +
            "JOIN FETCH sf.specialisation sp " +
            "JOIN FETCH sf.domain d " +
            "WHERE s.id = :id")
    List<StudentFilter> findAllByStudentId(@Param("id") Long studentId);

    @Query("SELECT sf FROM StudentFilter sf " +
            "JOIN FETCH sf.student s " +
            "JOIN FETCH sf.placement p " +
            "WHERE s.id = :id")
    List<StudentFilter> findStudentPlacementInfo(@Param("id") Long studentId);


}
