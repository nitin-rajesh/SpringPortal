package com.student.springerp.controller;

import com.student.springerp.crudops.StudentFilterRepo;
import com.student.springerp.entity.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students/info")
public class StudentFilterController {

    @Autowired
    private StudentFilterRepo studentFilterRepo;

    @GetMapping("/{id}")
    public ResponseEntity<List<StudentFilter>> getStudentFiltersByStudentId(@PathVariable("id") Long studentId) {
        List<StudentFilter> studentFilters = studentFilterRepo.findAllByStudentId(studentId);
        if (studentFilters.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 empty response
        }
        return ResponseEntity.ok(studentFilters);
    }

    @GetMapping("/placement/{id}")
    public ResponseEntity<List<StudentFilter>> getStudentPlacementInfo(@PathVariable("id") Long studentId) {
        List<StudentFilter> studentFilters = studentFilterRepo.findAllByStudentId(studentId);
        if (studentFilters.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 empty response
        }
        return ResponseEntity.ok(studentFilters);
    }
}
