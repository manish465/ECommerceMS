package com.manish.user.service;

import com.manish.user.dto.UserLoginDTO;
import com.manish.user.dto.UserRegistrationDTO;
import com.manish.user.entity.UserEntity;
import com.manish.user.exception.ApplicationException;
import com.manish.user.proxy.AuthProxy;
import com.manish.user.proxy.CartProxy;
import com.manish.user.repository.UserRepository;
import com.manish.user.utils.Convertor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserService {
    private final UserRepository userRepository;
    private final CartProxy cartProxy;
    private final AuthProxy authProxy;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> registerUser(@Valid @NotNull UserRegistrationDTO userRegistrationDTO){
        log.info("|| called register from UserService using {}||", userRegistrationDTO);

        Optional<UserEntity> existingUser = userRepository.findByUsername(userRegistrationDTO.getUsername());
        if(existingUser.isPresent()) throw new ApplicationException("User already exist");
        existingUser = userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(existingUser.isPresent()) throw new ApplicationException("User already exist");

        try {
            UserEntity user = UserEntity.builder()
                    .userId(UUID.randomUUID().toString())
                    .username(userRegistrationDTO.getUsername())
                    .firstname(userRegistrationDTO.getFirstname())
                    .lastname(userRegistrationDTO.getLastname())
                    .email(userRegistrationDTO.getEmail())
                    .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                    .roles(userRegistrationDTO.getRoles())
                    .build();


            ResponseEntity<String> cartId = cartProxy.createCart(user.getUserId());
            user.setCartId(cartId.getBody());

            return new ResponseEntity<>(userRepository.save(user).getUserId(), HttpStatus.CREATED);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<UserEntity> getUserByUserId(String userId){
        log.info("|| called getUserByUserId from UserService using {}||", userId);

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ApplicationException("User dose not exist");

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    public ResponseEntity<List<UserEntity>> getAllUser(){
        log.info("|| called getAllUser from UserService ||");

        List<UserEntity> userList = userRepository.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(String userId, @Valid @NotNull UserRegistrationDTO userRegistrationDTO){
        log.info("|| called updateUser from UserService using {}||", userRegistrationDTO);

        Optional<UserEntity> userExist = userRepository.findById(userId);

        if(userExist.isEmpty()) throw new ApplicationException("User dose not exist");

        try {
            UserEntity user = UserEntity.builder()
                    .userId(userId)
                    .username(userRegistrationDTO.getUsername())
                    .firstname(userRegistrationDTO.getFirstname())
                    .lastname(userRegistrationDTO.getLastname())
                    .email(userRegistrationDTO.getEmail())
                    .password(passwordEncoder.encode(userRegistrationDTO.getPassword()))
                    .roles(userRegistrationDTO.getRoles())
                    .build();

            userRepository.save(user);
            return new ResponseEntity<>("User Updated Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteUserByUserId(String userId) {
        log.info("|| called deleteUserByUserId from UserService using {}||", userId);

        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ApplicationException("User dose not exist");

        try {
            cartProxy.deleteCartByCartId(user.get().getCartId());
            userRepository.deleteById(userId);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteAll(){
        log.info("|| called deleteAll from UserService ||");

        try {
            cartProxy.deleteAll();
            userRepository.deleteAll();
            return new ResponseEntity<>("Users Deleted Successfully", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> login(@Valid @NotNull UserLoginDTO userLoginDTO){
        log.info("|| called login from UserService using {}||", userLoginDTO);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));


        log.info("|| doing authentication using UsernamePasswordAuthenticationToken with AuthenticationManager ||");
        if (!authentication.isAuthenticated())
            throw new ApplicationException("Invalid credentials");

        log.info("|| done authentication using UsernamePasswordAuthenticationToken with AuthenticationManager ||");
        String token = authProxy
                .generateToken(authentication.getName(), Convertor.extractAuthoritiesToString(authentication.getAuthorities()));

        Optional<UserEntity> userExist = userRepository.findByUsername(authentication.getName());
        if(userExist.isEmpty()) throw new ApplicationException("user not found");

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
