package com.home.profile.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class StudentRequestDTO {

    private UUID id;

    @NotEmpty
    @Size(min = 2, message = "Name should have at least 2 characters.")
    private String name;

    @NotEmpty
    @Email(message = "Email is not valid.")
    private String email;

    public StudentRequestDTO(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

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
}
