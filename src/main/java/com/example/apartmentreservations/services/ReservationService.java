package com.example.apartmentreservations.services;

import com.example.apartmentreservations.models.Reservation;
import com.example.apartmentreservations.entity.ReservationEntity;
import com.example.apartmentreservations.repositories.IReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service()
public class ReservationService implements IReservationService{

    private IReservationRepository reservationRepository;
    private final ModelMapper mapper;

    public ReservationService(IReservationRepository reservationRepository, ModelMapper mapper) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Reservation> GetReservationById(Integer id) {
        Optional<ReservationEntity> reservationFromDb= reservationRepository.findById(id);
        var reservation= mapper.map(reservationFromDb,Reservation.class);
        return Optional.of(reservation);
    }

    @Override
    public Reservation EditReservation(Reservation reservation) {
        ReservationEntity reservationFromDb = new ReservationEntity(reservation.getId(),reservation.getUserId(),reservation.getApartmentId(),reservation.getStartDay(),reservation.getEndDay());
        reservationRepository.save(reservationFromDb);
        return reservation;
    }

    @Override
    public Reservation CreateReservation(Reservation reservation) {
        var reservationForDb = mapper.map(reservation, ReservationEntity.class);
        ReservationEntity reservationEntity =reservationRepository.save(reservationForDb);
        reservation.setId(reservationEntity.getId());
        return reservation;
    }

    @Override
    public void DeleteReservation(Integer id) {
        reservationRepository.deleteById(id);

    }

    @Override
    public ArrayList<Reservation> UserReservations(Integer userId) {
        List<ReservationEntity> reservationEntities =reservationRepository.UserReservations(userId);
        var reservations = mapper.map(reservationEntities,new ArrayList<Reservation>().getClass());

        return reservations;
    }
}
