package com.example.apartmentreservations.models;

import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;

@Data
public class UpdateUserRequest {

    String fullName;
    String username;
    String password;
    @Email
    String email;
}
