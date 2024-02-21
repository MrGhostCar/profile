package com.home.profile.student;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

@WebMvcTest(StudentController.class)
public class StudentControllerTest extends JsonTest {

  @MockBean StudentService studentService;

  @MockBean WebClient webClient;

  @Autowired MockMvc mockMvc;

  @Test
  public void testNameValidation_invalidName() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO invalidEmailStudent = new StudentRequestDTO(null, "K", "kovacs@gmail.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testValidation_allFieldsCorrect() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO correctEmailStudent =
            new StudentRequestDTO(null, "Kovacs Bela", "kovacs@gmail.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
            .perform(
                    post("/student").contentType(APPLICATION_JSON).content(getJson(correctEmailStudent)))
            .andDo(print())
            .andExpect(status().isCreated());
  }

  @Test
  public void testEmailValidation_noEmailServer() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "kovacs@.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testEmailValidation_noTopDomain() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "kovacs@gmail.");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testEmailValidation_noUsername() throws Exception {
    StudentResponseDTO responseStudent = new StudentResponseDTO();
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "@gmail.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
