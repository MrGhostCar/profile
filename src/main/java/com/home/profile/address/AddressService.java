package com.home.profile.address;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AddressService {

  @Autowired private WebClient webClient;

  public AddressDTO getAddressByStudentId(UUID id) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder.path("/address").queryParam("userId", id).build())
        .headers(headers -> headers.setBasicAuth("profileService", "profileServicePass"))
        .retrieve()
        .bodyToMono(AddressDTO.class)
        .block();
  }
}
