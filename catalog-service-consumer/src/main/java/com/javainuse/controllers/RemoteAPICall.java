package com.javainuse.controllers;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javainuse.model.ProductInventoryResponse;

@FeignClient("INVENTORYSERVICE")
public interface RemoteAPICall {

	@RequestMapping("/api/inventory")
	List<ProductInventoryResponse> getInventoryLevels();

	@RequestMapping("/api/inventory/{productCode}")
	List<ProductInventoryResponse> getInventoryByProductCode(String productCode);
}
