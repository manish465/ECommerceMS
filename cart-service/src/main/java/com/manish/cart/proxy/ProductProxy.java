package com.manish.cart.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service/product")
public interface ProductProxy {
    @GetMapping("/price/")
    Double getProductPriceByProductId(@RequestParam String productId);
}
