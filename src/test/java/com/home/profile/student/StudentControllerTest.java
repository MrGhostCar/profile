package com.home.profile.student;

import static com.home.profile.student.MockMvcTestConfig.getJson;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentControllerTest.class)
public class StudentControllerTest {

  @MockBean StudentService studentService;

  @Autowired MockMvc mockMvc;

  @Test
  public void testValidation() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO requestStudent = new StudentRequestDTO(null, "a", "asd@asd.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(post("/student").contentType(APPLICATION_JSON).content(getJson(requestStudent)))
        .andExpect(status().isOk());
  }
}
