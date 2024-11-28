package com.student.springerp.crudops;

import com.student.springerp.entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface PlacementRepo extends JpaRepository<Placement, Long> {

    @Query("SELECT p FROM Placement p " +
            "JOIN PlacementFilter pf ON pf.placement = p " +
            "JOIN StudentFilter sf ON pf.specialisation = sf.specialisation " +
            "AND pf.domain = sf.domain " +
            "JOIN Student s ON sf.student = s " +
            "WHERE s.id = :id")
    List<Placement> findPlacementsForStudent(@Param("id") Long studentId);

}
