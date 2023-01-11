package com.example.apartmentreservations.controllers;

import com.example.apartmentreservations.models.LogInUser;
import com.example.apartmentreservations.models.TokenResponse;
import com.example.apartmentreservations.models.User;
import com.example.apartmentreservations.services.UserService;
import com.example.apartmentreservations.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoderService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;

    public AuthController(UserService userService, PasswordEncoder passwordEncoderService, AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil) {
        this.userService = userService;
        this.passwordEncoderService = passwordEncoderService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("registration")
    @CrossOrigin("*")
    public ResponseEntity<String> Create(@RequestBody @Valid User model, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<String>("Neuspešno kreiran nalog, proverite da li ste ispravno uneli sva polja!", HttpStatus.INTERNAL_SERVER_ERROR);
        }else {
            model.setPassword(passwordEncoderService.encode(model.getPassword()));
            var user=userService.createUser(model);
            return new ResponseEntity<String>("Uspešno kreiran nalog!", HttpStatus.OK);
        }
    }

    @PostMapping("login")
    @CrossOrigin("*")
    public TokenResponse userLogIn(@RequestBody LogInUser user) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword())
            );

        }catch (BadCredentialsException e){
            throw new Exception("Pogresno uneti podatci,pokusajte ponovo!",e);
        }

        final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        User logInUser = userService.getByUsername(user.getUsername());
        int id=logInUser.getId();
        String role=logInUser.getRole().name();
        TokenResponse tokenResponse = new TokenResponse(jwt, id, role);

        return tokenResponse;
    }
}
