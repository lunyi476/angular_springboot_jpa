package com.springbootjpaangular2.controlleradvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 
 * Any exception will come to this central advice
 *
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(
			ExceptionControllerAdvice.class);
	
	@ExceptionHandler(value=Exception.class)
	public void  exceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception exc) { 
		logger.debug(exc.getMessage()+" at "+req.getRequestURL(), exc);		
		res.setStatus(500);
	
		try {
			//Lazy error handling at server side, let Angular to handle and display		
			res.getWriter().print("Error :  " + exc.getMessage()+"  at "+req.getRequestURL());
			
		} catch (Exception er) {}
	}		
}
