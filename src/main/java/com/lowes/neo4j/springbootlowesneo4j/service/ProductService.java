package com.lowes.neo4j.springbootlowesneo4j.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.lowes.neo4j.springbootlowesneo4j.model.Product;
import com.lowes.neo4j.springbootlowesneo4j.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private static final String TOPIC = "EXAMPLE_TOPIC";


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    public void sendMessage(Product product) {
        logger.info(String.format("#### -> Producing message for ID : ", product.getId()));
        this.kafkaTemplate.send(TOPIC, product);
    }

    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); 
        logger.info("retrieved all the products");
        return products;
    }

    public Product getById(Long id) {
    	logger.info("call to fetch a record");
        return productRepository.findById(id).orElse(null);
    }

    public Product saveOrUpdate(Product product) {
        productRepository.save(product);
        logger.info("save/update a record");
        return product;
    }

    public void delete(Long id) {
    	logger.info("request to delete a record");
        productRepository.deleteById(id);

    }

    public Product saveOrUpdateProductForm (Product product) {
        Product savedProduct = saveOrUpdate(product);

        logger.info("saved a product");
        return savedProduct;
    }
}