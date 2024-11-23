package com.student.springerp.crudops;

import com.student.springerp.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepo extends JpaRepository<Domain, Long> {
}
