package com.student.springerp.controller;

import com.student.springerp.crudops.PlacementRepo;
import com.student.springerp.crudops.PlacementStudentRepo;
import com.student.springerp.crudops.StudentRepo;
import com.student.springerp.dto.PlacementStudentRequest;
import com.student.springerp.entity.Placement;
import com.student.springerp.entity.PlacementStudent;
import com.student.springerp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    @PostMapping("/{id}/upload-cv")
    public ResponseEntity<String> uploadCvApplication(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile pdfFile) {

        if (pdfFile.isEmpty() || !pdfFile.getContentType().equalsIgnoreCase("application/pdf")) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file.");
        }

        try {
            // Find the PlacementStudent entry by ID
            PlacementStudent placementStudent = placementStudentRepo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("PlacementStudent with ID " + id + " not found."));

            // Set the PDF file as a byte array in cvApplication
            placementStudent.setCvApplication(pdfFile.getBytes());

            // Save the updated entity
            placementStudentRepo.save(placementStudent);

            return ResponseEntity.ok("CV application uploaded successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/download-cv")
    public ResponseEntity<byte[]> downloadCvApplication(@PathVariable Long id) {

        // Find the PlacementStudent entry by ID
        PlacementStudent placementStudent = placementStudentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PlacementStudent with ID " + id + " not found."));

        byte[] cvApplication = placementStudent.getCvApplication();

        if (cvApplication == null || cvApplication.length == 0) {
            return ResponseEntity.notFound().build();
        }

        // Prepare HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("cv_application_" + id + ".pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(cvApplication);
    }

}

