package com.lowes.neo4j.springbootlowesneo4j.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.neo4j.springbootlowesneo4j.config.KakfaConfiguration;
import com.lowes.neo4j.springbootlowesneo4j.model.Product;
import com.lowes.neo4j.springbootlowesneo4j.service.ProductService;

@RestController
@RequestMapping(value = "/neo")
public class NeoController {
	private static final Logger logger = LoggerFactory.getLogger(KakfaConfiguration.class);

	private ProductService productService;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping("/product")
	public List<Product> listProducts() {
		return productService.listAll();
	}

	@RequestMapping("/product/show/{id}")
	public Product getProduct(@PathVariable String id) {
		if(id == null)
		{
			logger.error("Incomplete data");
			return null;
		}
		return productService.getById(Long.valueOf(id));
	}

	@PostMapping("/publish")
	public String sendMessageToKafkaTopic(@RequestBody Product product) {
		if(product == null || product.getCategory() == null || product.getName() == null)
		{
			logger.error("Incomplete data");
			return "Please enter required data";
		}
		this.productService.sendMessage(product);
		logger.info("record published");
		return "Record published successfully";
	}

	@RequestMapping("/product/delete/{id}")
	public String delete(@PathVariable String id) {
		if(id == null)
		{
			logger.error("Incomplete data");
			return "please enter valid data";
		}
		productService.delete(Long.valueOf(id));
		return "Deleted successfully";
	}
}