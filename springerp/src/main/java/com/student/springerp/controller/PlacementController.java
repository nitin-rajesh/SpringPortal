package com.student.springerp.controller;

import com.student.springerp.crudops.PlacementRepo;
import com.student.springerp.entity.Placement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/placements")
public class PlacementController {

    @Autowired
    private PlacementRepo placementRepo;

    @GetMapping("/student/{id}")
    public ResponseEntity<List<Placement>> getPlacementsForStudent(@PathVariable("id") Long studentId) {
        List<Placement> placements = placementRepo.findPlacementsForStudent(studentId);
        if (placements.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(placements);
    }
}
