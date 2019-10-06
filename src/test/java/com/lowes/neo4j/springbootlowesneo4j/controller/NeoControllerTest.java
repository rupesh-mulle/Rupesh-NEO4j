package com.lowes.neo4j.springbootlowesneo4j.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.lowes.neo4j.springbootlowesneo4j.model.GeneralResponse;
import com.lowes.neo4j.springbootlowesneo4j.model.Product;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NeoControllerTest {
	 @Mock
	    private NeoController neoController;
	 
	 
	 private Product product;
	 
	 private String id;
	 
	 private List<Product> productList;
	 
	 private ResponseEntity<GeneralResponse> response;
	 
	 private GeneralResponse result;
	 
	 @Before
	    public void setup() {
	        product = new Product();
	    	product.setCategory("Outdoor");
	    	product.setName("test");
	    	
	    	id= "61";
	    	
	    	productList = new ArrayList<>();
	    	productList.add(product);
	        
	    }
	 
	  @Test
	    public void sendMessageToKafkaTopic() throws Exception {
	    	result = new GeneralResponse();
	    	result.setResponseMessage("Published Record successfully");
	    	when(neoController.sendMessageToKafkaTopic(product)).thenReturn(new ResponseEntity<GeneralResponse>(result, HttpStatus.OK));
	    	response = neoController.sendMessageToKafkaTopic(product);
	    	 assertEquals(HttpStatus.OK, response.getStatusCode());
	         JSONAssert.assertEquals("Published Record successfully", response.getBody().getResponseMessage(), true);
	    }
	    
	    @Test
	    public void productById() throws Exception{
	    	result = new GeneralResponse();
	    	result.setResponseMessage("Request processed");
	    	result.setResult(product);
	    	when(neoController.getProduct(id)).thenReturn(new ResponseEntity<GeneralResponse>(result, HttpStatus.OK));
	    	response = neoController.getProduct(id);
	    	assertEquals(HttpStatus.OK, response.getStatusCode());
	    	assertThat(response.getBody().getResult()).extracting("name").isNotEmpty();
	    }
	    
	    @Test
	    public void listProducts() throws Exception{
	    	result = new GeneralResponse();
	    	result.setResponseMessage("Request processed");
	    	result.setResult(product);
	    	when(neoController.listProducts()).thenReturn(new ResponseEntity<GeneralResponse>(result, HttpStatus.OK));
	    	
	    	response = neoController.listProducts();
	    	assertEquals(HttpStatus.OK, response.getStatusCode());
	    	assertThat(response.getBody()).isNotNull();
	    }

}
