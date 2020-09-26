package com.springbootjpaangular2.domain;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;  // switch to junit5
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.springbootjpaangular2.SpringBootWebApplication;
import com.springbootjpaangular2.configuration.DBConfigurationProperties;
import com.springbootjpaangular2.configuration.WebConfiguration;
import com.springbootjpaangular2.controllers.QuoteOfferController;
 


/** 
 * @author lyi
 * 08/2020
 */
//@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)  // JUNIT-5, @Order(n) and WebApplicationContext Autowired to work
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguration.class, DBConfigurationProperties.class,
		SpringBootWebApplication.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootIntegrationTest {
   
    @Autowired
    private  WebApplicationContext webApplicationContext;

    @Autowired
	private MockMvc mockMvc;
    
    
    @Before
    public void initTests() { 	
    	 mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    
    @Test
    @Order(1)
    public void testCreateJsonObject() throws Exception { 	   		
    	Quotes qt = mockQuotes("shouldCreateQuotes");
  
        byte[] r1Json = toJson(qt);

        MvcResult result =  this.mockMvc.perform(post("/savequote")
                .content(r1Json)
                .header("reqaction", "new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))  // going to server
                .andExpect(status().isOk())  // coming back from server response, 200 status code
                .andReturn();
        
        Integer req_no = Integer.valueOf(result.getResponse().getHeader("new_request_no"));
    	assertTrue(result.getResponse().getContentAsString() != null && req_no != null);
      
        Offers ofs = mockOffers(req_no, 1);
        List<Offers> detail = new ArrayList<Offers>();
        
        detail.add(ofs);
        ofs = mockOffers(req_no, 0);
        detail.add(ofs);
        qt.setOffers(detail);
        
        r1Json = toJson(qt);

        result =  mockMvc.perform(post("/savequote")
                .content(r1Json)
                .header("reqaction", "save")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    	
    	assertTrue(result.getResponse().getHeader("message").indexOf("Save quote succeed") != -1);  
        assertTrue(qt.getOffers().size() ==2);
        
    }
    
    
    @Test
    @Order(2)
    public void testUpdateWholeQuote() throws Exception {
    	
    	MvcResult result = mockMvc.perform(get("/listquotes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    	
    	ObjectMapper mapper = new ObjectMapper();
    	List<Quotes> qts = mapper.readValue(result.getResponse().getContentAsString(), 
    			new TypeReference<List<Quotes>>(){});
    	Quotes qt = QuoteOfferController.removeManySideParent(qts).get(0);
        
        qt.setRequested_by("Leonard Yi");
        qt.setAllocated_qty(new BigDecimal(1400.00));
        qt.setRequest_price(new BigDecimal(2100.00));
        qt.setDescription("update whole fields blindly");
        byte[] r1Json = toJson(qt);
        
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/savequote")
    			.content(r1Json)
    			.header("reqaction", "save")
    			.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
                                           
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().bytes(r1Json))
                    .andDo(MockMvcResultHandlers.print());	       
    }
   
    
    @Test
    @Order(3)
    public void testRetrieveQuote () throws Exception {	
    	Quotes r1 = mockQuotes("shouldCreateQuote");  	  
        byte[] r1Json = toJson(r1);

        MvcResult result =  this.mockMvc.perform(post("/savequote")
                .content(r1Json)
                .header("reqaction", "new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))  
                .andExpect(status().isOk()) 
                .andReturn();
        
        Integer req_no = Integer.valueOf(result.getResponse().getHeader("new_request_no"));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/getquote")
        		.queryParam("request_no", req_no.toString())
                .accept(MediaType.APPLICATION_JSON);          
        ObjectMapper map = new ObjectMapper();
        
        result = this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print()).andReturn();	 
        
        assertTrue(map.readValue(result.getResponse().getContentAsString(), 
        		Quotes.class).getRequest_no() != null);
        
    }
    
    
    private Offers mockOffers (Integer rfqNo, int a) {
    	Offers ofs = new Offers();
    	ofs.setOwner("BAMBOOROSE");
    	ofs.setRequest_no(rfqNo);  	 	
    	ofs.setOffer_date(new java.sql.Date(new Date().getTime()));
    	ofs.setSupplier("Ling");
    	ofs.setDescription("for testing");
    	ofs.setStatus("Open");
    	ofs.setCurrency("USD");
    	ofs.setPayment_type("COL");
    	ofs.setBuyer("Ling");
    	ofs.setVendor_name("Peter");
    	if (a ==1) {
    		ofs.setItem("fish");  
	     	
    	} else {
    		ofs.setItem("Pork");  
    	}
    			
    	return ofs;	
    	
    }
    
    
    private Quotes mockQuotes(String prefix) {
     	Quotes qts = new Quotes();
     	//qts.setRequest_No(91);
    	qts.setOwner("BAMBOOROSE");// non-auto generated key, must set value
    	qts.setItem("Beef");   	
    	qts.setDescription("Food Ingredient");
    	qts.setRequested_by("Lun Yi");
    	qts.setCategory("Food");
    	qts.setItem_class("First");
    	qts.setDivision("Food");
    	qts.setBrand("Stop-Shop");
    	qts.setModify_user("Lun Yi");
    	qts.setModify_ts(new Timestamp(new Date().getTime()));
    	
        return qts;
    }

    
    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}