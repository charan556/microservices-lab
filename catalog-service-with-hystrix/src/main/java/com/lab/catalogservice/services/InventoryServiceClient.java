package com.lab.catalogservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lab.catalogservice.utils.MyThreadLocalsHolder;
import com.lab.catalogservice.web.models.ProductInventoryResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryServiceClient {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private InventoryServiceFeignClient inventoryServiceFeignClient;
	
	// TODO; move this to config file
	private static final String INVENTORY_API_PATH = "http://inventoryservice/api/";

	/*
	 * @Autowired public InventoryServiceClient(RestTemplate restTemplate,
	 * InventoryServiceFeignClient inventoryServiceFeignClient) { this.restTemplate
	 * = restTemplate; this.inventoryServiceFeignClient =
	 * inventoryServiceFeignClient; }
	 */

	@HystrixCommand(fallbackMethod = "getDefaultProductInventoryLevels", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public List<ProductInventoryResponse> getProductInventoryLevels() {
		return this.inventoryServiceFeignClient.getInventoryLevels();
	}

	List<ProductInventoryResponse> getDefaultProductInventoryLevels() {
		log.info("Returning default product inventory levels");
		return new ArrayList<>();
	}

	@HystrixCommand(fallbackMethod = "getDefaultProductInventoryByCode")
	public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode) {
		log.info("CorrelationID: " + MyThreadLocalsHolder.getCorrelationId());
		ResponseEntity<ProductInventoryResponse> itemResponseEntity = restTemplate
				.getForEntity(INVENTORY_API_PATH + "inventory/{code}", ProductInventoryResponse.class, productCode);

		/*
		 * //Simulate Delay try { java.util.concurrent.TimeUnit.SECONDS.sleep(5); }
		 * catch (InterruptedException e) { e.printStackTrace(); }
		 */

		if (itemResponseEntity.getStatusCode() == HttpStatus.OK) {
			Integer quantity = itemResponseEntity.getBody().getAvailableQuantity();
			log.info("Available quantity: " + quantity);
			return Optional.ofNullable(itemResponseEntity.getBody());
		} else {
			log.error("Unable to get inventory level for product_code: " + productCode + ", StatusCode: "
					+ itemResponseEntity.getStatusCode());
			return Optional.empty();
		}
	}

	Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode) {
		log.info("Returning default ProductInventoryByCode for productCode: " + productCode);
		log.info("CorrelationID: " + MyThreadLocalsHolder.getCorrelationId());
		ProductInventoryResponse response = new ProductInventoryResponse();
		response.setProductCode(productCode);
		response.setAvailableQuantity(50);
		return Optional.ofNullable(response);
	}

}
