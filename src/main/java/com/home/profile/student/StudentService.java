package com.home.profile.student;

import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModelMapper modelMapper;

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) throws StudentValidationException {
        Student student = modelMapper.map(studentRequestDTO, Student.class);
        validateStudent(student);
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentResponseDTO.class);
    }

    private void validateStudent(Student student) throws StudentValidationException {
        boolean emailValidated = emailValidation(student.getEmail());
        if (!emailValidated)
            throw new StudentValidationException("Student email is not valid.");
    }

    public List<StudentResponseDTO> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentResponseDTO> studentDTOs = new ArrayList<>();
        students.forEach(student -> studentDTOs.add(modelMapper.map(student, StudentResponseDTO.class)));
        return studentDTOs;
    }

    public void updateStudent(StudentRequestDTO studentRequestDTO) {
        Student student = modelMapper.map(studentRequestDTO, Student.class);
        studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }

    private boolean emailValidation(String emailAddress) {
        String regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
