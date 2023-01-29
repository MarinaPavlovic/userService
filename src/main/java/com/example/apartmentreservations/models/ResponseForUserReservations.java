package com.example.apartmentreservations.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseForUserReservations {
    @NotNull
    private Integer apartmentId;
    @NotNull
    private Integer reservationId;
    @NotNull
    private Integer userHostId;
    @NotNull
    private Integer userUserId;
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
    private List<String> images;
    @NotNull
    private LocalDate startDay;
    @NotNull
    private LocalDate endDay;
    @NotNull
    private long totalDays;
    @NotNull
    private double totalPrice;
}
