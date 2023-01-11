package com.example.apartmentreservations.services;

import com.example.apartmentreservations.models.Reservation;

import java.util.ArrayList;
import java.util.Optional;

public interface IReservationService {
    Optional<Reservation> GetReservationById (Integer id);
    Reservation EditReservation (Reservation reservation);
    Reservation CreateReservation(Reservation reservation);
    void DeleteReservation (Integer id);
    ArrayList<Reservation> UserReservations (Integer userId);


}
