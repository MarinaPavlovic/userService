package com.example.apartmentreservations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Column(name = "apartment_id")
    private Integer apartmentId;
    @NotNull
    @Column(name = "start_day")
    private Date startDay;
    @NotNull
    @Column(name = "end_day")
    private Date endDay;

}
