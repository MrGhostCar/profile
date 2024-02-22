package com.home.profile.student;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.home.profile.address.AddressService;
import com.home.profile.student.dto.StudentRequestDTO;
import com.home.profile.student.dto.StudentResponseDTO;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

@WebMvcTest(StudentController.class)
public class StudentControllerTest extends JsonTestConfig {

  @MockBean StudentService studentService;

  @MockBean WebClient webClient;

  @MockBean AddressService addressService;

  @Autowired MockMvc mockMvc;

  @Test
  public void testNameValidation_invalidName() throws Exception {
    StudentRequestDTO invalidEmailStudent = new StudentRequestDTO(null, "K", "kovacs@gmail.com");

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testValidation_allFieldsCorrect() throws Exception {
    StudentResponseDTO responseStudent =
        new StudentResponseDTO(UUID.randomUUID(), "Kovacs Bela", "kovacs@gmail.com");
    StudentRequestDTO correctStudentRequest =
        new StudentRequestDTO(null, "Kovacs Bela", "kovacs@gmail.com");

    Mockito.when(studentService.createStudent(any())).thenReturn(responseStudent);

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(correctStudentRequest)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", Matchers.is(correctStudentRequest.getName())));
  }

  @Test
  public void testEmailValidation_noEmailServer() throws Exception {
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "kovacs@.com");

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testEmailValidation_noTopDomain() throws Exception {
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "kovacs@gmail.");

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testEmailValidation_noUsername() throws Exception {
    StudentRequestDTO invalidEmailStudent =
        new StudentRequestDTO(null, "Kovacs Bela", "@gmail.com");

    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
