package com.home.profile.address;

import com.home.profile.exception.MicroserviceException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddressService {
  Logger logger = LoggerFactory.getLogger(AddressService.class);

  @Value("${microservices.username}")
  String userName;

  @Value("${microservices.password}")
  String password;

  @Autowired private WebClient webClient;

  public AddressDTO getAddressByStudentId(UUID id) throws MicroserviceException {

    try {
      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder.path("/address").queryParam("userId", id).build())
          .headers(headers -> headers.setBasicAuth(userName, password))
          .retrieve()
          .bodyToMono(AddressDTO.class)
          .block();
    } catch (Exception e) {
      logger.error("Error while reaching address service: {}", e.getMessage());
      throw new MicroserviceException(e.getMessage());
    }
  }
}
