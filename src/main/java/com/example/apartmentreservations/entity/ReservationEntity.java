package com.example.apartmentreservations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "reservation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "user_id")
    private Integer userId;
    @NotNull
    @Column(name= "host_id")
    private Integer hostId;
    @NotNull
    @Column(name = "apartment_id")
    private Integer apartmentId;
    @NotNull
    @Column(name = "start_day")
    private LocalDate startDay;
    @NotNull
    @Column(name = "end_day")
    private LocalDate endDay;


}
