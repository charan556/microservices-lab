package com.javainuse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.controllers.RemoteAPICall;
import com.javainuse.model.ProductInventoryResponse;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableCircuitBreaker
@EnableFeignClients
@ComponentScan({ "com" })
@EntityScan("com.lab.catalogservice.entities")
@EnableJpaRepositories("com.lab.catalogservice.repositories")
public class SpringBootHelloWorldApplication2 {

	@Autowired
	RemoteAPICall client;

	@RequestMapping("/")
	public List<ProductInventoryResponse> hello() {
		return client.getInventoryLevels();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication2.class, args);
	}

}