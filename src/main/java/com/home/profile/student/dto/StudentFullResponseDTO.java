package com.home.profile.student.dto;

import com.home.profile.address.AddressDTO;
import java.util.UUID;

public class StudentFullResponseDTO {

  private UUID id;

  private String name;

  private String email;

  private AddressDTO address;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
  }
}
