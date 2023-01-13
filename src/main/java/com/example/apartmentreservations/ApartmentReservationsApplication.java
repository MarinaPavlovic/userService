package com.example.apartmentreservations;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApartmentReservationsApplication {

    @Bean
    public ModelMapper modelMapper(){ return new ModelMapper();}
    public static void main(String[] args) {
        SpringApplication.run(ApartmentReservationsApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }



}
