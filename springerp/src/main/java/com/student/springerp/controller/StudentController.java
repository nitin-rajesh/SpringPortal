package com.student.springerp.controller;

import com.student.springerp.crudops.StudentRepo;
import com.student.springerp.dto.AuthRequest;
import com.student.springerp.entity.Student;
import com.student.springerp.service.JwtUtil;
import com.student.springerp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        // Authenticate the user
        Student student = studentRepo.findByEmail(authRequest.getUsername());

        if (student != null && passwordEncoder.matches(authRequest.getPassword(), student.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            throw new BadCredentialsException("Invalid username or password");
        }

        // Generate JWT token
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Optional<Student> student = studentRepo.findById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/register")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws URISyntaxException {
        String hashedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(hashedPassword);

        Student savedStudent = studentRepo.save(student);
        return ResponseEntity.created(new URI("/students/"+savedStudent.getId())).body(savedStudent);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteClient(@PathVariable long id) {
        studentRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
