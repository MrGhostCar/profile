package com.home.profile.student;

import com.home.profile.student.dto.StudentFullResponseDTO;
import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/student")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
        return new ResponseEntity<>(studentResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/student")
    public List<StudentResponseDTO> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/student/{id}")
    public StudentFullResponseDTO getFullStudent(@PathVariable UUID id) {
        return studentService.getFullStudent();
    }

    @PutMapping("/student")
    public void updateStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        studentService.updateStudent(studentRequestDTO);
    }

    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
    }

}
