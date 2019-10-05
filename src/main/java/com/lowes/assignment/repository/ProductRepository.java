package com.lowes.assignment.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.lowes.assignment.model.Product;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}
