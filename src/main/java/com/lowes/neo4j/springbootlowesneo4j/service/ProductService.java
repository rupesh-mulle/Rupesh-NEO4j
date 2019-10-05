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
        return products;
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveOrUpdate(Product product) {
        productRepository.save(product);
        return product;
    }

    public void delete(Long id) {
        productRepository.deleteById(id);

    }

    public Product saveOrUpdateProductForm (Product product) {
        Product savedProduct = saveOrUpdate(product);

        System.out.println("Saved Product Id: " + product.getId());
        return savedProduct;
    }
}