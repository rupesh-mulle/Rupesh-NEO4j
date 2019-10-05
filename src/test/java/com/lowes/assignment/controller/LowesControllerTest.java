package com.lowes.assignment.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class LowesControllerTest {

	 @InjectMocks
	    private LowesController lowesController;
	 
	    private MockMvc mockMvc;
	 
	    @Before
	    public void setup() {
	        MockitoAnnotations.initMocks(this);
	        this.mockMvc = MockMvcBuilders.standaloneSetup(lowesController).build();
	    }
	 
	    @Test
	    public void sendMessageToKafkaTopic() throws Exception {
	        this.mockMvc.perform(get("/publish")).andExpect(status().isOk());
	    }

}
