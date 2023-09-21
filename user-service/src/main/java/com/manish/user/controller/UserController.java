package com.manish.user.controller;

import com.manish.user.dto.UserRegistrationDTO;
import com.manish.user.entity.UserEntity;
import com.manish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<String> register(@RequestBody UserRegistrationDTO userRegistrationDTO){
        log.info("|| called register from  UserController using {}||", userRegistrationDTO.toString());
        return userService.register(userRegistrationDTO);
    }

    @GetMapping("/")
    public ResponseEntity<UserEntity> getUserByUserId(@RequestParam String userId){
        return userService.getUserByUserId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUser(){
        return userService.getAllUser();
    }
}
