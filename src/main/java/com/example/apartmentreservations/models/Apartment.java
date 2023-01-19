package com.example.apartmentreservations.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Apartment {
        private Integer id;
        @NotNull
        private Integer userId;
        @NotNull
        @NotBlank
        private String name;
        @NotNull
        @NotBlank
        private String description;
        @NotNull
        @NotBlank
        private String country;
        @NotNull
        @NotBlank
        private String city;
        @NotNull
        @NotBlank
        private String adres;
        @NotNull
        private Double pricePerNight;
        @NotNull
        private String destinationType;
        @NotNull
        private List<String> images;




}
