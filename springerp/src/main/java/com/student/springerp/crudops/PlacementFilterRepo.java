package com.student.springerp.crudops;

import com.student.springerp.entity.PlacementFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementFilterRepo extends JpaRepository<PlacementFilter, Long> {
}
