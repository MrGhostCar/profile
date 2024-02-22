package com.home.profile.student;

import com.home.profile.address.AddressDTO;
import com.home.profile.address.AddressService;
import com.home.profile.exception.MicroserviceException;
import com.home.profile.student.dto.StudentFullResponseDTO;
import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

  Logger logger = LoggerFactory.getLogger(StudentService.class);

  @Autowired StudentRepository studentRepository;

  @Autowired ModelMapper modelMapper;

  @Autowired AddressService addressService;

  public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
    Student student = modelMapper.map(studentRequestDTO, Student.class);
    Student savedStudent = studentRepository.save(student);
    return modelMapper.map(savedStudent, StudentResponseDTO.class);
  }

  public List<StudentResponseDTO> getStudents() {
    List<Student> students = studentRepository.findAll();
    List<StudentResponseDTO> studentDTOs = new ArrayList<>();
    students.forEach(
        student -> studentDTOs.add(modelMapper.map(student, StudentResponseDTO.class)));
    return studentDTOs;
  }

  @Transactional
  public void updateStudent(StudentRequestDTO studentRequestDTO) {
    Student foundStudent =
        studentRepository
            .findWithLockById(studentRequestDTO.getId())
            .orElseThrow(EntityNotFoundException::new);

    Student student = modelMapper.map(studentRequestDTO, Student.class);
    studentRepository.save(student);
  }

  @Transactional
  public void deleteStudent(UUID id) {
    Student foundStudent =
        studentRepository.findWithLockById(id).orElseThrow(EntityNotFoundException::new);
    studentRepository.deleteById(id);
  }

  public StudentFullResponseDTO getFullStudent(UUID id) throws MicroserviceException {
    StudentFullResponseDTO studentDTO;
    Student student = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);

    AddressDTO address = addressService.getAddressByStudentId(id);
    studentDTO = modelMapper.map(student, StudentFullResponseDTO.class);
    studentDTO.setAddress(address);
    return studentDTO;
  }

  public Student getStudentById(UUID id) {
    Optional<Student> foundStudent = studentRepository.findById(id);
    return foundStudent.orElse(null);
  }
}
