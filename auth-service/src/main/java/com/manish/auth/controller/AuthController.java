package com.manish.auth.controller;

import com.manish.auth.dto.ClaimsDataDTO;
import com.manish.auth.exception.ApplicationException;
import com.manish.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @GetMapping("/create")
    public String generateToken(@RequestParam String username, @RequestParam String roles){
        log.info("|| called generateToken from AuthController ||");

        return JwtService.generateToken(username, roles);
    }

    @GetMapping("/validate")
    public ClaimsDataDTO validateToken(@RequestParam String token){
        log.info("|| called validateToken from AuthController ||");

        if(!JwtService.validateToken(token)) throw new ApplicationException("Invalid access");

        log.info("|| token is valid ||");

        String username = JwtService.getUsernameFromToken(token);
        Claims claims = JwtService.getAllClaimsFromToken(token);

        log.info("|| after validating got data username : {} claims : {}", username, claims);

        return new ClaimsDataDTO(username, (String) claims.get("roles"));
    }
}
