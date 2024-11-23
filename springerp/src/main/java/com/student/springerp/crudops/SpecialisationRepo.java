package com.student.springerp.crudops;

import com.student.springerp.entity.Specialisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialisationRepo extends JpaRepository<Specialisation, Long> {
}
