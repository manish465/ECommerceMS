package com.manish.user.service;

import com.manish.user.dto.UserRegistrationDTO;
import com.manish.user.entity.UserEntity;
import com.manish.user.exception.ApplicationException;
import com.manish.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<String> registerUser(@Valid UserRegistrationDTO userRegistrationDTO){
        log.info("|| called register from  UserService using {}||", userRegistrationDTO.toString());

        Optional<UserEntity> existingUser = userRepository.findByUsername(userRegistrationDTO.getUsername());

        if(existingUser.isPresent()) throw new ApplicationException("User already exist");

        existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());

        if(existingUser.isPresent()) throw new ApplicationException("User already exist");

        try {
            UserEntity user = UserEntity.builder()
                    .username(userRegistrationDTO.getUsername())
                    .firstname(userRegistrationDTO.getFirstname())
                    .lastname(userRegistrationDTO.getLastname())
                    .email(userRegistrationDTO.getEmail())
                    .password(userRegistrationDTO.getPassword())
                    .roles(userRegistrationDTO.getRoles())
                    .build();

            userRepository.save(user);

            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<UserEntity> getUserByUserId(String userId){
        log.info("|| called getUserByUserId from  UserService using {}||", userId);

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ApplicationException("User dose not exist");

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    public ResponseEntity<List<UserEntity>> getAllUser(){
        log.info("|| called getAllUser from  UserService ||");

        List<UserEntity> userList = userRepository.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(String userId, @Valid UserRegistrationDTO userRegistrationDTO){
        log.info("|| called updateUser from  UserService using {}||", userRegistrationDTO.toString());

        Optional<UserEntity> userExist = userRepository.findById(userId);

        if(userExist.isEmpty()) throw new ApplicationException("User dose not exist");

        try {
            UserEntity user = UserEntity.builder()
                    .userId(userId)
                    .username(userRegistrationDTO.getUsername())
                    .firstname(userRegistrationDTO.getFirstname())
                    .lastname(userRegistrationDTO.getLastname())
                    .email(userRegistrationDTO.getEmail())
                    .password(userRegistrationDTO.getPassword())
                    .roles(userRegistrationDTO.getRoles())
                    .build();

            userRepository.save(user);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteUserByUserId(String userId) {
        log.info("|| called deleteUserByUserId from  UserService using {}||", userId);

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ApplicationException("User dose not exist");

        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
}
