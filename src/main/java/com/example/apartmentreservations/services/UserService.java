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

import java.util.*;

@Service()
public class UserService implements IUserService , UserDetailsService {
    private final IUserRepository userRepository;
    private final ModelMapper mapper;

    public UserService(IUserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
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
            if(request.getFullName() != null){
                userEntity.setFullName(request.getFullName());
            }
            if(request.getEmail() != null){
                userEntity.setEmail(request.getEmail());
            }
            if(request.getUsername() != null){
                userEntity.setUsername(request.getUsername());
            }
            if(request.getPassword() != null){
                userEntity.setPassword(request.getPassword());
            }
            userRepository.save(userEntity);
            return mapper.map(userEntity, User.class);
        }

        throw new NoSuchElementException(String.format("User with given id %s could not be found", id));

    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
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
