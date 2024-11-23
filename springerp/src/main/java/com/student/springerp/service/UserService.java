package com.student.springerp.service;

import com.student.springerp.crudops.StudentRepo;
import com.student.springerp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String registerUser(String plainPassword) {
        // Hash the password
        return passwordEncoder.encode(plainPassword);
    }

    public boolean validatePassword(String plainPassword, String hashedPassword) {
        // Validate the password
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    public Student findByEmailAndPassword(String email, String rawPassword) {
        // Find student by email
        Student student = studentRepo.findByEmail(email);
        if (student != null && passwordEncoder.matches(rawPassword, student.getPassword())) {
            return student; // Password matches
        }
        return null; // Either email or password is incorrect
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(username);
        if (student == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Return a UserDetails implementation
        return org.springframework.security.core.userdetails.User
                .withUsername(student.getEmail())
                .password(student.getPassword()) // Make sure passwords are hashed
                .authorities("ROLE_USER") // Assign roles/authorities if needed
                .build();
    }
}
