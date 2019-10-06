package com.lowes.neo4j.springbootlowesneo4j.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.lowes.neo4j.springbootlowesneo4j.model.Product;
import com.lowes.neo4j.springbootlowesneo4j.repository.ProductRepository;

@Service
public class ConsumerService {
	
	 @Autowired
	 ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @KafkaListener(topics = "EXAMPLE_TOPIC")
    public void consume(String prodString) throws IOException {
        logger.info(String.format("Consumed record is -> ", prodString));
        Gson g = new Gson(); 
        Product product = g.fromJson(prodString, Product.class);
         Product prod = new Product();
         prod.setName(product.getName());
         prod.setCategory(product.getCategory());
         prod = productRepository.save(prod);
         logger.info("Inserted following record into Neo4j", prodString);
    }
}