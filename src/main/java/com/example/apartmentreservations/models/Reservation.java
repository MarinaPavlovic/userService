package com.example.apartmentreservations.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private Integer hostId;
    @NotNull
    private Integer apartmentId;
    @NotNull
    private LocalDate startDay;
    @NotNull
    private LocalDate endDay;

}
