package com.springbootjpaangular2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/** 
 * @author lyi
 * 08/2020
 */
@SpringBootApplication 
public class SpringBootWebApplication {
    public static void main(String[] args) {
    	//AnnotationConfigEmbeddedWebApplicationContext 
    	ConfigurableApplicationContext ctx = SpringApplication.run(
    			SpringBootWebApplication.class, args);     
    }
}