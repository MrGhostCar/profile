package com.home.profile.address;

import java.util.UUID;

public class AddressDTO {

  private UUID id;
  private String address;

  public AddressDTO(UUID id, String address) {
    this.id = id;
    this.address = address;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
