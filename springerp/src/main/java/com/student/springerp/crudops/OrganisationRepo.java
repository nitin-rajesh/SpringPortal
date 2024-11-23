package com.student.springerp.crudops;

import com.student.springerp.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepo extends JpaRepository<Organisation, Long> {
}
