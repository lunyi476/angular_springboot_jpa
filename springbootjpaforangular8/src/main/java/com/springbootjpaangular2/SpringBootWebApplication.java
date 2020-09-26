package com.springbootjpaangular2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



/** 
 * @author lyi
 * 08/2020
 * 
 * Algorithm to determine WebApplicationType:
 * If Spring MVC is present, an AnnotationConfigServletWebServerApplicationContext is used
 * If Spring MVC is not present and Spring WebFlux is present, an AnnotationConfigReactiveWebServerApplicationContext is used
 * Otherwise, AnnotationConfigApplicationContext is used
 */
@SpringBootApplication
public class SpringBootWebApplication {
    public static void main(String[] args) {
    	//AnnotationConfigServletWebServerApplicationContext
    	ConfigurableApplicationContext ctx = SpringApplication.run(
    			SpringBootWebApplication.class, args);
    }
}