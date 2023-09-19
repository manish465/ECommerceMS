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

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<String> register(@Valid UserRegistrationDTO userRegistrationDTO){
        log.info("|| called register from  UserService using {}||", userRegistrationDTO.toString());

        Optional<UserEntity> existingUser = userRepository
                .findByUsernameAndEmail(userRegistrationDTO.getUsername(), userRegistrationDTO.getEmail());

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
}
