package com.example.apartmentreservations.services;

import com.example.apartmentreservations.models.LogInUser;
import com.example.apartmentreservations.models.UpdateUserRequest;
import com.example.apartmentreservations.models.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);


    User getUserById(int id);
    User editUser(int id, UpdateUserRequest user);
    void deleteUser(Integer id);
    List<User> getAll();

}
