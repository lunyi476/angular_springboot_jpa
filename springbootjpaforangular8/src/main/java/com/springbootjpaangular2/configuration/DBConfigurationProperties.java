package com.springbootjpaangular2.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/** 
 * @author lyi
 * 08/2020
 */
@Configuration  
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
//property in *.properties file: spring.datasource.url, prefix are valid to bind to this object
@ConfigurationProperties(prefix="spring.datasource", ignoreUnknownFields = false)
public class DBConfigurationProperties {
	
	/**
	 * different application-context will have different Environment,
	 * then get different properties.
	 */
	//@Autowired
	//private Environment env;
	/**
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
    **/
  
    // Use JPA persistenceUnit instead of above properties of mapping or environment
	@Bean  
    public static EntityManagerFactory entityManagerFactoryBean () {  
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
