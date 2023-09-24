package com.manish.cart.service;

import com.manish.cart.entity.CartEntity;
import com.manish.cart.entity.Product;
import com.manish.cart.exception.ApplicationException;
import com.manish.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public ResponseEntity<String> deleteAll(){
        log.info("|| called deleteAll from  CartService ||");

        try {
            cartRepository.deleteAll();
            return new ResponseEntity<>("Deleted All Cart", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> createCart(String creatorId){
        log.info("|| called createCart from  CartService using {} ||", creatorId);

        Optional<CartEntity> cartExist = cartRepository.findByCreatorId(creatorId);
        if(cartExist.isPresent()) throw new ApplicationException("cart already exist for the user");

        try {
            CartEntity cart = CartEntity.builder()
                    .creatorId(creatorId)
                    .productList(new ArrayList<>())
                    .totalPrice(0)
                    .build();

            CartEntity savedCart = cartRepository.save(cart);
            return new ResponseEntity<>(savedCart.getCartId(), HttpStatus.CREATED);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> addProduct(String cartId, String productId){
        log.info("|| called addProduct from  CartService using {} and {} ||", cartId, productId);

        Optional<CartEntity> cartExist = cartRepository.findById(cartId);
        if(cartExist.isEmpty()) throw new ApplicationException("cart dose not exist");

        try {
            Optional<Product> productExist = cartExist
                    .get()
                    .getProductList()
                    .stream()
                    .filter(p -> Objects.equals(p.getProductId(), productId))
                    .findFirst();

            if(productExist.isPresent()){
                productExist.get().setQuantity(productExist.get().getQuantity() + 1);
            }else {
                cartExist.get().getProductList().add(new Product(productId, 1));
            }

            // TODO: update the totalPrice
            cartRepository.save(cartExist.get());
            return new ResponseEntity<>("Product Added To Cart", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> removeProduct(String cartId, String productId){
        log.info("|| called removeProduct from  CartService using {} and {} ||", cartId, productId);

        Optional<CartEntity> cartExist = cartRepository.findById(cartId);
        if(cartExist.isEmpty()) throw new ApplicationException("cart dose not exist");

        try {
            Optional<Product> productExist = cartExist
                    .get()
                    .getProductList()
                    .stream()
                    .filter(p -> Objects.equals(p.getProductId(), productId))
                    .findFirst();

            if(productExist.isPresent()){
                productExist.get().setQuantity(productExist.get().getQuantity() - 1);

                if(productExist.get().getQuantity() == 0)
                    cartExist.get().getProductList().remove(productExist.get());

            }else {
                throw new ApplicationException("product dose not exist");
            }

            // TODO: update the totalPrice
            cartRepository.save(cartExist.get());
            return new ResponseEntity<>("Product Removed From Cart", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<List<CartEntity>> getAllCart(){
        log.info("|| called getAllCart from  CartService ||");

        List<CartEntity> cartList = cartRepository.findAll();

        return new ResponseEntity<>(cartList, HttpStatus.OK);
    }

    public ResponseEntity<CartEntity> getCartByCartId(String cartId){
        log.info("|| called getCartByCartId from  CartService using {} ||", cartId);

        Optional<CartEntity> cartExist = cartRepository.findById(cartId);
        if(cartExist.isEmpty()) throw new ApplicationException("cart dose not exist");

        try {
            return new ResponseEntity<>(cartExist.get(), HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteCartByCartId(String cartId){
        log.info("|| called deleteCartByCartId from  CartService using {} ||", cartId);

        Optional<CartEntity> cartExist = cartRepository.findById(cartId);
        if(cartExist.isEmpty()) throw new ApplicationException("cart dose not exist");

        try {
            cartRepository.deleteById(cartId);

            return new ResponseEntity<>("Cart Deleted", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
}