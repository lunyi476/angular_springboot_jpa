package com.springbootjpaangular2.configuration;

//import org.apache.catalina.connector.Connector;
import org.h2.server.web.WebServlet;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

/** 
 * @author lyi
 * 08/2020
 */
@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
//@EnableConfigurationProperties annotation is strictly connected to @ConfiguratonProperties
@EnableConfigurationProperties(DBConfigurationProperties.class)
public class WebConfiguration {
	
	// The Console lets you access a SQL database using a browser interface
    @Bean 
    ServletRegistrationBean<?> h2servletRegistration(){
        ServletRegistrationBean<WebServlet> registrationBean = 
        		new ServletRegistrationBean<>(new WebServlet());
        
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
    
    @Bean
    public TerminatBean getTerminateBean() {
        return new TerminatBean();
    }
    
	@Bean  
	public MessageSource messageSource() { 
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	     
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	// Tomcat 8.5 does not allow [] in encoding, to allow it
	/**@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
	        @Override
	        public void customize(Connector connector) {
	            connector.setProperty("relaxedQueryChars", "|{}[]");
	        }
	    });
	    return factory;
	}**/
}
