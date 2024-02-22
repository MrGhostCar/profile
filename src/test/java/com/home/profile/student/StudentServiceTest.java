package com.home.profile.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

  @InjectMocks StudentService studentService;

  @Mock StudentRepository studentRepository;
  @Mock AddressService addressService;
  @Spy
  ModelMapper modelMapper;

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

  @Test
  void testNonExistentStudentUpdate() {
    StudentRequestDTO studentRequestDTO =
        new StudentRequestDTO(UUID.randomUUID(), "aaaaa", "asd@asd.com");

    when(studentRepository.findWithLockById(any())).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class,
        () -> {
          studentService.updateStudent(studentRequestDTO);
        });
  }

  @Test
  void testGetFullUser() throws MicroserviceException {
    Student studentFromDb = new Student(UUID.randomUUID(), "aaaaa", "asd@asd.com");
    AddressDTO responseAddressDTO = new AddressDTO(UUID.randomUUID(), "Deak ter 1.");

    when(studentRepository.findById(any())).thenReturn(Optional.of(studentFromDb));
    when(addressService.getAddressByStudentId(any())).thenReturn(responseAddressDTO);

    StudentFullResponseDTO fullStudent = studentService.getFullStudent(studentFromDb.getId());

    assertEquals(fullStudent.getId(), studentFromDb.getId());
    assertEquals(fullStudent.getName(), studentFromDb.getName());
    assertEquals(fullStudent.getEmail(), studentFromDb.getEmail());

    assertEquals(fullStudent.getAddress().getId(), responseAddressDTO.getId());
    assertEquals(fullStudent.getAddress().getAddress(), responseAddressDTO.getAddress());
  }
}
