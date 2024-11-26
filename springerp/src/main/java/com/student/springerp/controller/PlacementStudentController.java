package com.student.springerp.controller;

import com.student.springerp.crudops.PlacementRepo;
import com.student.springerp.crudops.PlacementStudentRepo;
import com.student.springerp.crudops.StudentRepo;
import com.student.springerp.dto.PlacementStudentRequest;
import com.student.springerp.entity.Placement;
import com.student.springerp.entity.PlacementStudent;
import com.student.springerp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/placement-student")
public class PlacementStudentController {

    @Autowired
    private PlacementStudentRepo placementStudentRepo;

    @Autowired
    private PlacementRepo placementRepo;

    @Autowired
    private StudentRepo studentRepo;


    // Fetch PlacementStudent details by ID
    @GetMapping("/{id}")
    public ResponseEntity<List<PlacementStudent>> getPlacementStudentDetails(@PathVariable Long id) {
        List<PlacementStudent> placementStudentList = placementStudentRepo.findPlacementStudentWithDetailsById(id);

        if (placementStudentList == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(placementStudentList);
    }

    // Fetch all PlacementStudent entries
    @GetMapping
    public ResponseEntity<List<PlacementStudent>> getAllPlacementStudents() {
        List<PlacementStudent> placementStudents = placementStudentRepo.findAllPlacementStudentsWithDetails();

        if (placementStudents.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(placementStudents);
    }

    @PostMapping("/apply")
    public ResponseEntity<PlacementStudent> createPlacementStudent(@RequestBody PlacementStudentRequest request) {
        // Fetch Placement and Student by their IDs
        Placement placement = placementRepo.findById(request.getPlacementId())
                .orElseThrow(() -> new IllegalArgumentException("Placement not found"));
        Student student = studentRepo.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // Create and save PlacementStudent entity
        PlacementStudent placementStudent = new PlacementStudent();
        placementStudent.setPlacement(placement);
        placementStudent.setStudent(student);

        PlacementStudent savedPlacementStudent = placementStudentRepo.save(placementStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlacementStudent);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlacementStudent> createPlacementStudent(
            @RequestParam("placement_id") Long placementId,
            @RequestParam("student_id") Long studentId,
            @RequestParam("cv_application") MultipartFile cvApplication) {

        try {
            // Fetch Placement and Student entities by their IDs
            Placement placement = placementRepo.findById(placementId)
                    .orElseThrow(() -> new IllegalArgumentException("Placement not found"));
            Student student = studentRepo.findById(studentId)
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));

            // Create PlacementStudent entity
            PlacementStudent placementStudent = new PlacementStudent();
            placementStudent.setPlacement(placement);
            placementStudent.setStudent(student);

            // Set the CV application file as byte array
            if (!cvApplication.isEmpty()) {
                try {
                    placementStudent.setCvApplication(cvApplication.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Set default values
            placementStudent.setAbout("");  // Default empty about
            placementStudent.setAcceptance(false);  // Default acceptance to false
            placementStudent.setComments("");  // Default empty comments
            placementStudent.setDate(new Date());  // Set today's date

            // Save the entity
            PlacementStudent savedPlacementStudent = placementStudentRepo.save(placementStudent);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPlacementStudent);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}

