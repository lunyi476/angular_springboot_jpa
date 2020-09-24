package com.springbootjpaangular2.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.constraints.NotNull;


/** 
 * @author lyi
 * 08/2020
 */
@Configuration  
@PropertySource("classpath:application.properties")  // it is default
//property in *.properties file: spring.datasource.url, prefix are valid to bind to this object
@ConfigurationProperties(prefix="spring.datasource", ignoreUnknownFields = false)
public class DBConfigurationProperties {
	
	/**
	 * different application-context will have different Environment,
	 * then get/reach different properties.
	 * 
	 * StandardServletEnvironment for AnnotationConfigServletWebServerApplicationContext
	 * https://spring.io/blog/2014/11/04/a-quality-qualifier
	 */
	//@Autowired
    //@Qualifier("main")
	//private Environment env;
	 
    @NotNull   
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String url;
    
    @NotNull
    private String driver;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setDriver (String driver) {
    	this.driver = driver;
    }
  
    // use JPA persistenceUnit instead of above properties
	@Bean  
    public EntityManagerFactory entityManagerFactoryBean () {  
    	EntityManagerFactory entityManagerFactory;
		try {
			entityManagerFactory = Persistence.
					createEntityManagerFactory("SpringBootWebOracle"); 
			
		} catch (Throwable ex) {
			System.err.println("Initial EntityManagerFactory creation failed."+ ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		return entityManagerFactory;
	}
}
