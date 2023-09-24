package com.manish.user.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cart-service/cart")
public interface CartProxy {
    @PostMapping("/add/")
    ResponseEntity<String> createCart(@RequestParam String creatorId);

    @DeleteMapping("/all")
    ResponseEntity<String> deleteAll();

    @DeleteMapping("/")
    ResponseEntity<String> deleteCartByCartId(@RequestParam String cartId);
}
