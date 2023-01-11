package com.example.apartmentreservations.repositories;

import com.example.apartmentreservations.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {


    @Query(nativeQuery = true, value = "SELECT * FROM user WHERE username= :username")
    UserEntity findByUsername(@Param("username") String username);
}
