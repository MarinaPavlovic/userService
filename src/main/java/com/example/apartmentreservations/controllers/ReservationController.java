package com.example.apartmentreservations.controllers;

import com.example.apartmentreservations.models.Reservation;
import com.example.apartmentreservations.services.IReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final IReservationService reservationService;

    public ReservationController(IReservationService reservationService) {
        this.reservationService = reservationService;
    }



    @GetMapping("{id}")
    @CrossOrigin("*")
    public Optional<Reservation> GetById(Integer id){
        return reservationService.GetReservationById(id);
    }

    @PostMapping("edit")
    @CrossOrigin("*")
    public ResponseEntity<?> EditReservation (@RequestBody @Valid Reservation reservation, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Reservation>(reservationService.EditReservation(reservation), HttpStatus.OK);
    }

    @PostMapping("create")
    @CrossOrigin("*")
    public ResponseEntity<?> CreateReservation (@RequestBody @Valid Reservation reservation, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Reservation>(reservationService.CreateReservation(reservation), HttpStatus.OK);
    }

    @PostMapping("delete")
    @CrossOrigin("*")
    public void DeleteReservation(Integer id){
        reservationService.DeleteReservation(id);
    }

    @GetMapping()
    @CrossOrigin("*")
    public ArrayList<Reservation> UserReservations (Integer userId){
        return reservationService.UserReservations(userId);
    }


}
