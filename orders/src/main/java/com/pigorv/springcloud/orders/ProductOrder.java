package com.pigorv.springcloud.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products")
public interface ProductOrder {
    @GetMapping("/{productName}")
    String getProductName(@PathVariable String productName);


}
