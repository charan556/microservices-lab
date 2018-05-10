package com.lab.catalogservice.services;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lab.catalogservice.web.models.ProductInventoryResponse;

@FeignClient(value = "INVENTORYSERVICE")
public interface InventoryServiceFeignClient {

    @RequestMapping("/api/inventory")
    List<ProductInventoryResponse> getInventoryLevels();

    @RequestMapping("/api/inventory/{productCode}")
    List<ProductInventoryResponse> getInventoryByProductCode(String productCode);

}
