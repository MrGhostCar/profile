package com.home.profile.student;

import java.util.List;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class StudentRepositoryTest {
  @Autowired StudentRepository studentRepository;

  @Test
  public void testCRUD() {
    Student student = new Student(UUID.randomUUID(), "Toth Elemer", "elemer@asd.com");
    studentRepository.save(student);
    List<Student> students = studentRepository.findAll();

    Assertions.assertThat(students).extracting(Student::getName).containsOnly(student.getName());

    studentRepository.deleteAll();
    Assertions.assertThat(studentRepository.findAll()).isEmpty();
  }

}
