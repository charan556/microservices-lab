package com.lab.catalogservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javainuse.controllers.RemoteAPICall;
import com.lab.catalogservice.entities.Product;
import com.lab.catalogservice.repositories.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private RemoteAPICall apiCall;

	public List<Product> findAllProducts() {
		List<Product> products = productRepository.findAll();
		final Map<String, Integer> inventoryLevels = getInventoryLevelsWithFeignClient();
		final List<Product> availableProducts = products.stream()
				.filter(p -> inventoryLevels.get(p.getCode()) != null && inventoryLevels.get(p.getCode()) > 0)
				.collect(Collectors.toList());
		return availableProducts;
	}

	private Map<String, Integer> getInventoryLevelsWithFeignClient() {
		log.info("Fetching inventory levels using FeignClient");
		Map<String, Integer> inventoryLevels = new HashMap<>();
		List<com.javainuse.model.ProductInventoryResponse> inventory = apiCall.getInventoryLevels();
		for (com.javainuse.model.ProductInventoryResponse item : inventory) {
			inventoryLevels.put(item.getProductCode(), item.getAvailableQuantity());
		}
		log.debug("InventoryLevels: {}", inventoryLevels);
		return inventoryLevels;
	}
}
