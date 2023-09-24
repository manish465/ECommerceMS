package com.manish.product.controller;

import com.manish.product.dto.AddProductDTO;
import com.manish.product.entity.ProductEntity;
import com.manish.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody AddProductDTO addProductDTO){
        log.info("|| called addProduct from  ProductController using {} ||", addProductDTO.toString());
        return productService.addProduct(addProductDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        log.info("|| called getAllProducts from  ProductController ||");
        return productService.getAllProducts();
    }

    @GetMapping("/product/")
    public ResponseEntity<ProductEntity> getProductByProductId(@RequestParam String productId){
        log.info("|| called getProductByProductId from  ProductController using {} ||", productId);
        return productService.getProductByProductId(productId);
    }

    @GetMapping("/vendor/")
    public ResponseEntity<List<ProductEntity>> getProductsByVendorId(@RequestParam String vendorId){
        log.info("|| called getProductsByVendorId from  ProductController using {} ||", vendorId);
        return productService.getProductsByVendorId(vendorId);
    }

    @GetMapping("/price/")
    public Double getProductPriceByProductId(@RequestParam String productId){
        log.info("|| called getProductPriceByProductId from  ProductController using {} ||", productId);
        return productService.getProductPriceByProductId(productId);
    }

    @PutMapping("/product/")
    public ResponseEntity<String> updateProductByProductId(@RequestParam String productId,
                                                           @RequestBody AddProductDTO addProductDTO){
        log.info("|| called updateProductByProductId from  ProductController using {} and {} ||",
                productId, addProductDTO.toString());
        return productService.updateProductByProductId(productId, addProductDTO);
    }

    @DeleteMapping("/product/")
    public ResponseEntity<String> deleteProductByProductId(@RequestParam String productId){
        log.info("|| called deleteProductByProductId from  ProductController using {} ||", productId);
        return productService.deleteProductByProductId(productId);
    }

    @DeleteMapping("/vendor/")
    public ResponseEntity<String> deleteProductsByVendorId(@RequestParam String vendorId){
        log.info("|| called deleteProductsByVendorId from  ProductController using {} ||", vendorId);
        return productService.deleteProductsByVendorId(vendorId);
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAll(){
        log.info("|| called deleteProductsByVendorId from  ProductController ||");
        return productService.deleteAll();
    }
}
