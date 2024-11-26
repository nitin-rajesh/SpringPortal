package com.student.springerp.crudops;

import com.student.springerp.entity.PlacementStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacementStudentRepo extends JpaRepository<PlacementStudent, Long> {
    @Query("SELECT ps FROM PlacementStudent ps " +
            "JOIN FETCH ps.placement p " +
            "JOIN FETCH ps.student s")
    List<PlacementStudent> findAllPlacementStudentsWithDetails();

    @Query("SELECT ps FROM PlacementStudent ps " +
            "JOIN FETCH ps.placement p " +
            "JOIN FETCH ps.student s " +
            "WHERE s.id = :id")
    List<PlacementStudent> findPlacementStudentWithDetailsById(@Param("id") Long studentId);
}
