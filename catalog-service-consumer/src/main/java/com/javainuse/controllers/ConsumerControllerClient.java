package com.javainuse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.catalogservice.entities.Product;
import com.lab.catalogservice.services.ProductService;

@RestController
public class ConsumerControllerClient {

	@Autowired
	private ProductService productService;

	@RequestMapping("/products")
	public List<Product> getInventory() {

		try {
			return productService.findAllProducts();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}