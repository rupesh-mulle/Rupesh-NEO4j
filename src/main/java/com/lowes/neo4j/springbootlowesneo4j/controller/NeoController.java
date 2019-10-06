package com.lowes.neo4j.springbootlowesneo4j.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.neo4j.springbootlowesneo4j.model.Product;
import com.lowes.neo4j.springbootlowesneo4j.service.ProductService;

@RestController
@RequestMapping(value = "/neo")
public class NeoController {

	
private ProductService productService;


@Autowired
public void setProductService(ProductService productService) {
    this.productService = productService;
}


@RequestMapping("/product")
public List<Product> listProducts(){
    return productService.listAll();
}

@RequestMapping("/product/show/{id}")
public Product getProduct(@PathVariable String id){
    return productService.getById(Long.valueOf(id));
}

@PostMapping("/publish")
public String sendMessageToKafkaTopic(@RequestBody Product product) {
    this.productService.sendMessage(product);
    return "Record publish with ID :"+product.getId();
}

@RequestMapping("/product/delete/{id}")
public String delete(@PathVariable String id){
    productService.delete(Long.valueOf(id));
    return "Deleted successfully";
}
}