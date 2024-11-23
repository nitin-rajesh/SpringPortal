package com.student.springerp.crudops;

import com.student.springerp.entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlacementRepo extends JpaRepository<Placement, Long> {
}
