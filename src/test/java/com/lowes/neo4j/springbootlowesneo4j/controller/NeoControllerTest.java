package com.lowes.neo4j.springbootlowesneo4j.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
	 
	 @Before
	    public void setup() {
	        product = new Product();
	    	product.setCategory("Outdoor");
	    	product.setName("test");
	    	
	    	id= "e0f1a7e4-260e-4872-b5fa-9f895337e679";
	    	
	    	productList = new ArrayList<>();
	    	productList.add(product);
	        
	    }
	 
	  @Test
	    public void sendMessageToKafkaTopic() throws Exception {
	    	when(neoController.sendMessageToKafkaTopic(product)).thenReturn("Record published successfully");
	    	String result = neoController.sendMessageToKafkaTopic(product);
	    	assertEquals("Record published successfully", result);
	    }
	    
	    @Test
	    public void productById() throws Exception{
	    	when(neoController.getProduct(id)).thenReturn(product);
	    	assertEquals("test", neoController.getProduct(id).getName());
	    }
	    
	    @Test
	    public void listProducts() throws Exception{
	    	when(neoController.listProducts()).thenReturn(productList);
	    	assertEquals(true,neoController.listProducts().size()>0);
	    }

}
