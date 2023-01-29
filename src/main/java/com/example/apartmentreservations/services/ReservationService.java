package com.example.apartmentreservations.services;

import com.example.apartmentreservations.models.*;
import com.example.apartmentreservations.entity.ReservationEntity;
import com.example.apartmentreservations.repositories.IReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.stream.Collectors;

@Service()
public class ReservationService implements IReservationService{

    private IReservationRepository reservationRepository;
    private final ModelMapper mapper;
    private RestTemplate restTemplate;
    private IUserService userService;


    public ReservationService(IReservationRepository reservationRepository, ModelMapper mapper, RestTemplate restTemplate, IUserService userService) {
        this.reservationRepository = reservationRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.userService = userService;
    }

    @Override
    public Optional<Reservation> GetReservationById(Integer id) {
        Optional<ReservationEntity> reservationFromDb= reservationRepository.findById(id);
        var reservation= mapper.map(reservationFromDb,Reservation.class);
        return Optional.of(reservation);
    }


    @Override
    public Reservation EditReservation(Reservation reservation) {
        ReservationEntity reservationFromDb = new ReservationEntity(reservation.getId(),reservation.getUserId(),reservation.getHostId(),reservation.getApartmentId(),reservation.getStartDay(),reservation.getEndDay());
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
    public List<ResponseForUserReservations> UserReservations(Integer userId) {
        User user=userService.getUserById(userId);
        List<ReservationEntity>reservationEntities=new ArrayList<ReservationEntity>();
        if(user.role.name()=="USER") {
            reservationEntities = reservationRepository.UserReservations(userId);
        }else {
            reservationEntities=reservationRepository.HostReservations(userId);
        }

        List<Integer> apartmentsId = new ArrayList<Integer>();
        for(ReservationEntity res:reservationEntities){
            apartmentsId.add(res.getApartmentId());
        }
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(apartmentsId);
        ResponseEntity<Apartment[]> apartmentMS=QuestForApartmentMS(reservationRequest);
        List<ResponseForUserReservations> responseForFront=ResponseForFront(apartmentMS,reservationEntities);

        return responseForFront;
    }

    @Override
    public ResponseEntity<Apartment[]> QuestForApartmentMS(ReservationRequest apartmentsId) {
        String APARTMENT_URL_MS="http://localhost:1313/";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequest> httpEntity=new HttpEntity<ReservationRequest>(apartmentsId,headers);
        ResponseEntity<Apartment[]> result=(restTemplate.exchange(APARTMENT_URL_MS+"apartment/reservations", HttpMethod.POST,httpEntity,Apartment[].class));
        return result;
    }

    @Override
    public List<ResponseForUserReservations> ResponseForFront(ResponseEntity<Apartment[]> apartments, List<ReservationEntity> reservations) {
        List<Apartment> apartments1= mapper.map(apartments.getBody(), new ArrayList<Apartment>().getClass());
        List<ResponseForUserReservations> reservationsResponse = new ArrayList<ResponseForUserReservations>();
        for(ReservationEntity res: reservations){
            for(Apartment ap : apartments1){
                if (ap.getId()==res.getApartmentId()){
                    long totalDays= ChronoUnit.DAYS.between(res.getStartDay(),res.getEndDay());
                    double totalPrice= totalDays*ap.getPricePerNight();
                    ResponseForUserReservations model = new ResponseForUserReservations(ap.getId(),res.getId(),ap.getUserId(),res.getUserId(),ap.getName(),ap.getDescription(),ap.getCountry(),ap.getCity(),ap.getAdres(),ap.getPricePerNight(),ap.getImages(),res.getStartDay(),res.getEndDay(),totalDays,totalPrice);
                    reservationsResponse.add(model);
                }
            }
        }
        reservationsResponse=reservationsResponse.stream().distinct().collect(Collectors.toList());
        return reservationsResponse;
    }

    @Override
    public List<Integer> responseForApartmentMS() {
        List<ReservationEntity> allReservations= reservationRepository.findAll();
        List<Integer>apartmentsId= new ArrayList<Integer>();
        for(ReservationEntity res: allReservations){
            apartmentsId.add(res.getApartmentId());
        }
        return  apartmentsId;
    }
}
