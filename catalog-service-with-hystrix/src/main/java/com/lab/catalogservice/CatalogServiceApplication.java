package com.lab.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableCircuitBreaker
//@SpringBootApplication
@SpringBootApplication
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@ComponentScan({ "com" })
@EntityScan("com.lab.catalogservice.entities")
@EnableJpaRepositories("com.lab.catalogservice.repositories")
public class CatalogServiceApplication {
	
   
	@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

	public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}
