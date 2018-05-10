package com.lab.catalogservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lab.catalogservice.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
