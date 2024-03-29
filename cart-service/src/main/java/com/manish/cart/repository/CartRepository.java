package com.manish.cart.repository;

import com.manish.cart.entity.CartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartEntity, String> {
    Optional<CartEntity> findByCreatorId(String creatorId);
}
