package com.manish.cart.controller;

import com.manish.cart.entity.CartEntity;
import com.manish.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(){
        return cartService.deleteAll();
    }

    @PostMapping("/add/")
    public ResponseEntity<String> createCart(@RequestParam String creatorId){
        return cartService.createCart(creatorId);
    }

    @PutMapping("/add/")
    public ResponseEntity<String> addProduct(@RequestParam String cartId, @RequestParam String productId){
        return cartService.addProduct(cartId, productId);
    }

    @PutMapping("/remove/")
    public ResponseEntity<String> removeProduct(@RequestParam String cartId, @RequestParam String productId){
        return cartService.removeProduct(cartId, productId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CartEntity>> getAllCart(){
        return cartService.getAllCart();
    }

    @GetMapping("/")
    public ResponseEntity<CartEntity> getCartByCartId(@RequestParam String cartId){
        return cartService.getCartByCartId(cartId);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteCartByCartId(@RequestParam String cartId){
        return cartService.deleteCartByCartId(cartId);
    }
}
