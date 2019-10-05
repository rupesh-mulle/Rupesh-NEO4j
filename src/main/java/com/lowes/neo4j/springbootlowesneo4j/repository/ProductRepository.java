package com.lowes.neo4j.springbootlowesneo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.lowes.neo4j.springbootlowesneo4j.model.Product;

public interface ProductRepository extends Neo4jRepository<Product, Long> {
}
