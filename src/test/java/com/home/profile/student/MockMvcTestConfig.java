package com.home.profile.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class MockMvcTestConfig {

    @Autowired
    MockMvc mockMvc;

    protected static final ObjectMapper mapper = new ObjectMapper();
    protected static ObjectWriter ow;

    @BeforeAll
    protected static void init() {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    protected static String getJson(Object input) throws JsonProcessingException {
        return ow.writeValueAsString(input);
    }

}