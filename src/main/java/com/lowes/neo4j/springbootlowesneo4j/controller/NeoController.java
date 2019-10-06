package com.lowes.neo4j.springbootlowesneo4j.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.neo4j.springbootlowesneo4j.config.KakfaConfiguration;
import com.lowes.neo4j.springbootlowesneo4j.model.GeneralResponse;
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

	@RequestMapping("/product/showAll")
	public ResponseEntity<GeneralResponse> listProducts() {
		

    	ResponseEntity<GeneralResponse> response = null;
    	GeneralResponse result = new GeneralResponse();
    	try {
    	List<Product> productList = productService.listAll();
    	
    	if(productList == null) {
    		result.setResponseMessage("No records in the database");
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);
    		return response;
    	}
    	else {
    		result.setResponseMessage("Request processed successfully");
        	result.setResult(productList);
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);
    		return response;
    		
    	}
    	}
    	catch(Exception e) {
    		result.setResponseMessage("Exception Occured in /product/showAll");
    		result.setErrorMessage(e.getMessage());
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    		return response;
    	}
    
	}

	@RequestMapping("/product/find/{id}")
	public ResponseEntity<GeneralResponse> getProduct(@PathVariable String id) {

    	ResponseEntity<GeneralResponse> response = null;
    	GeneralResponse result = new GeneralResponse();
    	try {
    		
    	if(id == null)
		{
			logger.error("Incomplete data");

    		result.setResponseMessage("Request did not process");
    		result.setErrorMessage("Please enter valid Id");
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.BAD_REQUEST);
    		return response;
		}
    	else {
    		Product product = productService.getById(Long.valueOf(id));
    		if(product == null) {
    			result.setResponseMessage("Request processed successfully");
    			result.setErrorMessage("No record with given Id");
        		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);
        		return response;
    		}
    		result.setResponseMessage("Request processed successfully");
        	result.setResult(product);
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);
    		return response;
    	}
    	}
    	catch(Exception e) {
    		result.setResponseMessage("Exception Occured in /product/find/{id}");
    		result.setErrorMessage(e.getMessage());
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    		return response;
    	}
    
	}

	@PostMapping("/publish")
	public ResponseEntity<GeneralResponse> sendMessageToKafkaTopic(@RequestBody Product product) {
		ResponseEntity<GeneralResponse> response = null;
    	GeneralResponse result = new GeneralResponse();
    	try {
    	if(product == null || product.getCategory() == null || product.getName() == null)
		{
			logger.error("Incomplete data");
			result.setResponseMessage("Request did not process");
			result.setErrorMessage("Please provide mandatory fields category and Name");
			response = new ResponseEntity<GeneralResponse>(result, HttpStatus.BAD_REQUEST);
			return response;
		}
    	else {
    		 	this.productService.sendMessage(product);
    	        logger.info("Published record");
    	        result.setResponseMessage("Request processed successfully");
    	        result.setResult("Record inserted");
    			response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);
    			return response;
    	}
    	}
    	catch(Exception e) {
    		result.setResponseMessage("Exception Occured in /Publish");
    		result.setErrorMessage(e.getMessage());
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    		return response;
    	}
	}

	@RequestMapping("/product/delete/{id}")
	public ResponseEntity<GeneralResponse> delete(@PathVariable String id) {

    	ResponseEntity<GeneralResponse> response = null;
    	GeneralResponse result = new GeneralResponse();
    	
    	try {
    	if(id == null)
		{
			logger.error("Incomplete data");

    		result.setResponseMessage("Request did not process");
    		result.setErrorMessage("Please enter valid Id");
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.BAD_REQUEST);;
    		return response;
		}
    	else{
    		productService.delete(Long.valueOf(id));
    		result.setResponseMessage("Request processed successfully");
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.OK);;
    		return response;
    	}
    	}
    	catch(Exception e) {
    		result.setResponseMessage("Exception Occured in /product/delete/{id}");
    		result.setErrorMessage(e.getMessage());
    		response = new ResponseEntity<GeneralResponse>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    		return response;
    	}
	}
}