package com.manish.user.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth-service/auth")
public interface AuthProxy {
    @GetMapping("/create/")
    String generateToken(@RequestParam String username, @RequestParam String roles);
}
