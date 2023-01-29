package com.example.apartmentreservations.controllers;

import com.example.apartmentreservations.models.LogInUser;
import com.example.apartmentreservations.models.UpdateUserRequest;
import com.example.apartmentreservations.models.User;
import com.example.apartmentreservations.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoderService;

    public UserController(IUserService userService, PasswordEncoder passwordEncoderService) {
        this.userService = userService;
        this.passwordEncoderService = passwordEncoderService;
    }

    @GetMapping("all")
    @CrossOrigin("*")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("create")
    @CrossOrigin("*")
    public ResponseEntity<?> Create(@RequestBody @Valid User model, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            model.setPassword(passwordEncoderService.encode(model.getPassword()));
            return new ResponseEntity<User>(userService.createUser(model), HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    @CrossOrigin("*")
    public User GetUser(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @PatchMapping({"{id}"})
    @CrossOrigin("*")
    public ResponseEntity<?> Edit(@PathVariable("id") int id, @RequestBody @Valid UpdateUserRequest user, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>(result.getAllErrors().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            user.setPassword(passwordEncoderService.encode(user.getPassword()));

            return new ResponseEntity<User>(userService.editUser(id, user), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    @CrossOrigin("*")
    public void DeleteUser(@PathVariable("id") Integer id){
        userService.deleteUser(id);
    }


}


