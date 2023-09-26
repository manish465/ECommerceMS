package com.manish.user.controller;

import com.manish.user.dto.UserLoginDTO;
import com.manish.user.dto.UserRegistrationDTO;
import com.manish.user.entity.UserEntity;
import com.manish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @NotNull UserRegistrationDTO userRegistrationDTO){
        log.info("|| called registerUser from  UserController using {}||", userRegistrationDTO);
        return userService.registerUser(userRegistrationDTO);
    }

    @GetMapping("/")
    public ResponseEntity<UserEntity> getUserByUserId(@RequestParam String userId){
        log.info("|| called getUserByUserId from  UserController using {}||", userId);
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUser(){
        log.info("|| called getAllUser from  UserController||");
        return userService.getAllUser();
    }

    @PutMapping("/")
    public ResponseEntity<String> updateUser(@RequestParam String userId,
                                             @RequestBody @NotNull UserRegistrationDTO userRegistrationDTO){
        log.info("|| called updateUser from  UserController using {} and {}||",
                userId, userRegistrationDTO.toString());
        return userService.updateUser(userId, userRegistrationDTO);
    }

    @DeleteMapping("/delete/")
    public ResponseEntity<String> deleteUserByUserId(@RequestParam String userId){
        log.info("|| called deleteUserByUserId from  UserController using {}||", userId);
        return userService.deleteUserByUserId(userId);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<String> deleteAll(){
        log.info("|| called deleteAll from  UserController ||");
        return userService.deleteAll();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @NotNull UserLoginDTO userLoginDTO){
        log.info("|| called login from  UserController using {}||", userLoginDTO);
        return userService.login(userLoginDTO);
    }
}
