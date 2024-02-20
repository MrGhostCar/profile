package com.home.profile.student;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.home.profile.student.dto.StudentRequestDTO;
import org.junit.jupiter.api.Test;

public class StudentIntegrationTests extends MockMvcTestConfig {

  StudentRequestDTO correctStudent =
      new StudentRequestDTO(null, "Kovacs Endre", "kovacs.endre@gmail.com");
  StudentRequestDTO invalidEmailStudent = new StudentRequestDTO(null, "K", "kov@asd@gmail.com");

  @Test
  public void whenPostSuccessful_thenReturnCreated() throws Exception {
    mockMvc
        .perform(post("/student").contentType(APPLICATION_JSON).content(getJson(correctStudent)))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  public void givenEmailInvalid_whenPostMade_thenReturnBadRequest() throws Exception {
    mockMvc
        .perform(
            post("/student").contentType(APPLICATION_JSON).content(getJson(invalidEmailStudent)))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }
}
