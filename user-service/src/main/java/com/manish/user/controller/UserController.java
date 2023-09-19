package com.manish.user.controller;

import com.manish.user.dto.UserRegistrationDTO;
import com.manish.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
