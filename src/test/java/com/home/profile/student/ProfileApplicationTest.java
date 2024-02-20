package com.home.profile.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProfileApplicationTest {

  @Autowired StudentController studentController;

  @Test
  public void contextLoads() {
    Assertions.assertThat(studentController).isNot(null);
  }
}
