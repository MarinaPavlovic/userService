package com.example.apartmentreservations.services;

import com.example.apartmentreservations.models.UpdateUserRequest;
import com.example.apartmentreservations.models.User;
import com.example.apartmentreservations.entity.UserEntity;
import com.example.apartmentreservations.repositories.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service()
public class UserService implements IUserService , UserDetailsService {
    private final IUserRepository userRepository;
    private final ModelMapper mapper;
    private final RestTemplate restTemplate;


    public UserService(IUserRepository userRepository, ModelMapper mapper, RestTemplate restTemplate, RestTemplate restTemplate1) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.restTemplate = restTemplate1;
    }



    @Override
    public User createUser(User user) {
        UserEntity userForDb=new UserEntity(null,user.fullName,user.username,user.password,user.email,user.role, false);
        var savedUser =userRepository.save(userForDb);
        return mapper.map(savedUser,User.class);
    }

    @Override
    public User getUserById(int id) {
        Optional<UserEntity> userFromDb = userRepository.findById(id);

        if(userFromDb.isPresent()){
            return mapper.map(userFromDb, User.class);
        }

        throw new NoSuchElementException(String.format("User with given id %s could not be found", id));
    }

    @Override
    public User editUser(int id, UpdateUserRequest request) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        if(userEntityOptional.isPresent()){
            var userEntity = userEntityOptional.get();
            if(request.getFullName() != null && request.getFullName() !=""){
                userEntity.setFullName(request.getFullName());
            }
            if(request.getEmail() != null){
                userEntity.setEmail(request.getEmail());
            }
            if(request.getUsername() != null && request.getUsername() !=""){
                userEntity.setUsername(request.getUsername());
            }
            if(request.getPassword() != null && request.getPassword() !=""){
                userEntity.setPassword(request.getPassword());

            }
            userRepository.save(userEntity);
            return mapper.map(userEntity, User.class);
        }

        throw new NoSuchElementException(String.format("User with given id %s could not be found", id));

    }

    @Transactional
    @Override
    public void deleteUser(Integer id) {
        User user = getUserById(id);
        if(user.role.name()=="USER") {
            userRepository.deleteById(id);
        }else{
            requestForApartmentMS(id);
            userRepository.deleteById(id);

        }
    }

    public void requestForApartmentMS (Integer userId){
        String APARTMENT_MS_URL="http://localhost:1313/apartment/delete/all/{userId}";
        Map < String, String > params = new HashMap < String, String > ();
        params.put("userId", String.valueOf(userId));
        restTemplate.delete(APARTMENT_MS_URL,params);



    }

    @Override
    public List<User> getAll() {
        var models=mapper.map(userRepository.findAll(),new ArrayList<User>().getClass());
        return models;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found!");
        }

        List authorities = Arrays.asList(new SimpleGrantedAuthority( "User"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public User getByUsername (String username){
        UserEntity user = userRepository.findByUsername(username);
        return mapper.map(user,User.class);

    }
}
