package com.home.profile.student;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.home.profile.address.AddressService;
import com.home.profile.exception.MicroserviceException;
import com.home.profile.student.dto.StudentRequestDTO;
import com.jayway.jsonpath.JsonPath;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StudentIntegrationTests extends JsonTestConfig {

  @Autowired MockMvc mockMvc;

  @MockBean AddressService addressService;

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

  @Test
  public void givenNoUsersInDb_whenGetMadeWithAnId_thenReturn404() throws Exception {
    mockMvc
        .perform(
            get("/student/{id}", "4edb58d7-3a4e-4654-a4ef-aab4438a61b2")
                .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  public void givenNoAddressService_whenGetMade_thenReturnServerError() throws Exception {

    Mockito.when(addressService.getAddressByStudentId(any()))
        .thenThrow(new MicroserviceException("Conn refused"));

    MvcResult result =
        mockMvc
            .perform(
                post("/student").contentType(APPLICATION_JSON).content(getJson(correctStudent)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();

    String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc
        .perform(
            get("/student/{id}", "4edb58d7-3a4e-4654-a4ef-aab4438a61b2")
                .contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void givenPostWasMadeSuccessful_whenGetAllStudentsMade_thenReturnLastStudent()
      throws Exception {

    MvcResult result =
        mockMvc
            .perform(
                post("/student").contentType(APPLICATION_JSON).content(getJson(correctStudent)))
            .andDo(print())
            .andExpect(status().isCreated())
            .andReturn();

    String createdId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

    mockMvc
        .perform(get("/student").contentType(APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[*].id", Matchers.hasItem(createdId)));
  }
}
