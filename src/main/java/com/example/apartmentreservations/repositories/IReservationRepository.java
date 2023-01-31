package com.example.apartmentreservations.repositories;

import com.example.apartmentreservations.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM reservation WHERE user_id= :userId")
    List<ReservationEntity> UserReservations (@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM reservation WHERE host_id= :userId")
    List<ReservationEntity> HostReservations (@Param("userId") Integer userId);

    @Query(nativeQuery = true, value = "SELECT * FROM reservation WHERE apartment_id= :apartmentId")
    List<ReservationEntity> ApartmentReservations (@Param("apartmentId") Integer apartmentId);


}
