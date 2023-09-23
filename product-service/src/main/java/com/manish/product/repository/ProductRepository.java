package com.manish.product.repository;

import com.manish.product.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {
    List<ProductEntity> findAllByVendorId(String vendorId);
    void deleteAllByVendorId(String vendorId);
}
