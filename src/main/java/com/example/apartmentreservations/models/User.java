package com.example.apartmentreservations.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public Integer id;

    @NotNull
    @NotBlank
    public String fullName;
    @NotNull
    @NotBlank
    public String username;
    @NotNull
    @NotBlank
    public String password;
    @NotNull
    @NotBlank
    @Email
    public String email;
    @NotNull
    public UserRole role;
    @NotNull
    public boolean isDeleted;

}
