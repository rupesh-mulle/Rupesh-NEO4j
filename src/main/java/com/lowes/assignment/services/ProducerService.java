package com.lowes.assignment.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.lowes.assignment.model.Product;


@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);
    private static final String TOPIC = "EXAMPLE_TOPIC";

    @Autowired
    private KafkaTemplate<String, Product> kafkaTemplate;

    public UUID sendMessage(Product product) {
        logger.info(String.format("#### -> Producing message for ID : ", product.getId()));
        this.kafkaTemplate.send(TOPIC, product);
        return product.getId();
    }
}