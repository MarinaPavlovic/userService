package com.example.apartmentreservations.services;

import com.example.apartmentreservations.entity.ReservationEntity;
import com.example.apartmentreservations.models.Apartment;
import com.example.apartmentreservations.models.Reservation;
import com.example.apartmentreservations.models.ReservationRequest;
import com.example.apartmentreservations.models.ResponseForUserReservations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    Optional<Reservation> GetReservationById (Integer id);
    Reservation EditReservation (Reservation reservation);
    Reservation CreateReservation(Reservation reservation);
    void DeleteReservation (Integer id);
    List<ResponseForUserReservations> UserReservations (Integer userId);
    ResponseEntity<Apartment[]> QuestForApartmentMS (ReservationRequest apartmentsId);
    List<ResponseForUserReservations> ResponseForFront (ResponseEntity<Apartment[]> apartments, List<ReservationEntity> reservations);


}
