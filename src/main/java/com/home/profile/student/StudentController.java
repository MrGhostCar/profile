package com.home.profile.student;

import com.home.profile.exception.MicroserviceException;
import com.home.profile.student.dto.StudentFullResponseDTO;
import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import com.home.profile.util.Constants;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

  Logger logger = LoggerFactory.getLogger(StudentController.class);

  @Autowired StudentService studentService;

  @PostMapping("/student")
  public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
    if (studentRequestDTO.getId() != null) {
      logger.error(Constants.ID_ON_CREATION_MESSAGE);
      return new ResponseEntity<>(Constants.ID_ON_CREATION_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    StudentResponseDTO studentResponseDTO = studentService.createStudent(studentRequestDTO);
    return new ResponseEntity<>(studentResponseDTO, HttpStatus.CREATED);
  }

  @GetMapping("/student")
  public List<StudentResponseDTO> getStudents() {
    return studentService.getStudents();
  }

  @GetMapping("/student/{id}")
  public ResponseEntity<?> getFullStudent(@PathVariable UUID id) {
    StudentFullResponseDTO student;
    try {
      student = studentService.getFullStudent(id);
    } catch (MicroserviceException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (student == null)
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(student, HttpStatus.OK);
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
