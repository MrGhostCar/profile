package com.home.profile.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.home.profile.student.dto.StudentResponseDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @InjectMocks StudentService studentService;

  @Mock StudentRepository studentRepository;

  @Mock ModelMapper modelMapper = new ModelMapper();

  @Test
  void testFindAllStudents() {
    List<Student> students = new ArrayList<>();
    Student student1 = new Student(UUID.randomUUID(), "aaaaa", "asd@asd.com");
    Student student2 = new Student(UUID.randomUUID(), "bbbbb", "vvv@vvv.com");
    Student student3 = new Student(UUID.randomUUID(), "ccccc", "ddd@ddd.com");
    students.add(student1);
    students.add(student2);
    students.add(student3);

    when(studentRepository.findAll()).thenReturn(students);

    List<StudentResponseDTO> resultList = studentService.getStudents();

    assertEquals(3, resultList.size());
    verify(studentRepository, times(1)).findAll();
  }
}
