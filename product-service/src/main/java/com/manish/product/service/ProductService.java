package com.manish.product.service;

import com.manish.product.dto.AddProductDTO;
import com.manish.product.entity.ProductEntity;
import com.manish.product.exception.ApplicationException;
import com.manish.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<String> addProduct(@Valid AddProductDTO addProductDTO){
        log.info("|| called addProduct from  ProductService using {} ||", addProductDTO.toString());

        try {
            ProductEntity product = ProductEntity.builder()
                    .name(addProductDTO.getName())
                    .description(addProductDTO.getDescription())
                    .price(addProductDTO.getPrice())
                    .vendorId(addProductDTO.getVendorId())
                    .build();

            productRepository.save(product);

            return new ResponseEntity<>("Product Added", HttpStatus.CREATED);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        log.info("|| called getAllProduct from  ProductService ||");

        List<ProductEntity> productList = productRepository.findAll();

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    public ResponseEntity<ProductEntity> getProductByProductId(String productId){
        log.info("|| called getProductByProductId from  ProductService using {} ||", productId);

        Optional<ProductEntity> productExist = productRepository.findById(productId);
        if(productExist.isEmpty()) throw new ApplicationException("Product dose not exist");

        return new ResponseEntity<>(productExist.get(), HttpStatus.OK);
    }

    public ResponseEntity<List<ProductEntity>> getProductsByVendorId(String vendorId){
        log.info("|| called getProductByProductId from  ProductService using {} ||", vendorId);

        List<ProductEntity> productList = productRepository.findAllByVendorId(vendorId);

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    public ResponseEntity<String> updateProductByProductId(String productId, @Valid AddProductDTO addProductDTO){
        log.info("|| called updateProductByProductId from  UserService using {} and {} ||",
                productId, addProductDTO.toString());

        Optional<ProductEntity> productExist = productRepository.findById(productId);
        if(productExist.isEmpty()) throw new ApplicationException("Product dose not exist");

        try {
            ProductEntity product = ProductEntity.builder()
                    .productId(productId)
                    .name(addProductDTO.getName())
                    .description(addProductDTO.getDescription())
                    .price(addProductDTO.getPrice())
                    .vendorId(addProductDTO.getVendorId())
                    .build();

            productRepository.save(product);

            return new ResponseEntity<>("Product Updated", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteProductByProductId(String productId){
        log.info("|| called deleteProductByProductId from  ProductService using {} ||", productId);

        Optional<ProductEntity> productExist = productRepository.findById(productId);
        if(productExist.isEmpty()) throw new ApplicationException("Product dose not exist");

        try {
            productRepository.deleteById(productId);
            return new ResponseEntity<>("Product Deleted", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteProductsByVendorId(String vendorId){
        log.info("|| called deleteProductsByVendorId from  ProductService using {} ||", vendorId);

        try {
            productRepository.deleteAllByVendorId(vendorId);
            return new ResponseEntity<>("Products Deleted", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }

    public ResponseEntity<String> deleteAll(){
        log.info("|| called deleteAll from  ProductService ||");

        try {
            productRepository.deleteAll();
            return new ResponseEntity<>("All Products Deleted", HttpStatus.OK);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage());
        }
    }
}
