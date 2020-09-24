package com.springbootjpaangular2.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.springbootjpaangular2.services.QuoteOfferService;
import com.springbootjpaangular2.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
//import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
//org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder
import org.springframework.http.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * For handling request from Angular 8
 * return body (Object), ResponseEntity by json type and not string 
 * (that will trigger view mapping which we don't want) 
 * 
 * @author lyi
 * 08/2020
 *
 */
@Controller  
@CrossOrigin(origins="*")
public class QuoteOfferController {
	private static final Logger logger = LoggerFactory.getLogger(
			QuoteOfferController.class);
	
	public static String DEFAULT_OWNER = "BAMBOOROSE";
    private QuoteOfferService quoteOfferService;

    @Autowired
    public void setQuoteOfferService(QuoteOfferService quoteOfferService) 
    		throws ParseException {
        this.quoteOfferService = quoteOfferService;    
    }  
    /**
     * Used to get message from code, or use context to get MessageResource: 
     * WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext())
     */
    @Autowired
    private MessageSource  messageResource;
    /**
     * Validator and DataBinder make up the validation. Inside BeanWrapper, 
     * PropertyEditorSupport
     */
    @Autowired
	@Qualifier("todoValidator")
	private org.springframework.validation.Validator validator2;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) { //ServletRequestDataBinder
		 binder.setValidator(validator2);
		 binder.registerCustomEditor(Integer.class, "request_no", new QuotesEditor());
	}
	
    
    @GetMapping(value = "/enroll", produces = MediaType.APPLICATION_JSON_VALUE)  
    public ResponseEntity<String>  login (HttpServletRequest request, 
    		HttpServletResponse response)   {   
    	String user = request.getParameter("userName");
    	String pass = request.getParameter("password");
    	JSONObject resp = new JSONObject();
    	logger.info("user----"+user +"     "+"pass----"+pass);
    	HttpHeaders headers = new HttpHeaders();
    	/**
    	 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Access-Control-Expose-Headers
    	 */
        headers.add("Access-Control-Expose-Headers", "*");
        // Fake implementation 
    	if (StringUtils.isNotBlank(user) && StringUtils.isNotBlank(pass)) {  	 
    		 headers.add("loggedin", "1");
    		 return new ResponseEntity<String>(
    				  resp.toString(), headers,
    			      HttpStatus.OK);
    	}
    	else  {
    		 headers.add("loggedin", "0");
    		 return new ResponseEntity<String>(
   				  resp.toString(),
   			      HttpStatus.OK);		
    	}
    }
    
    /**
     * @Pathvariable Or @RequestParam("request_no") Quotes reqNo, 
     * can trigger Custom Editor which set request_no
     */
    @ResponseBody 
    @GetMapping(value = "/listquotes", produces = MediaType.APPLICATION_JSON_VALUE)  
    public  List<Quotes> listQuotes(
    		HttpServletRequest request, HttpServletResponse response) { 
    	List<Quotes> all = quoteOfferService.listAllQuotes();
    	all = removeManySideParent (all);	
    	return all;
    }
    
    @ResponseBody 
    @GetMapping(value = "/listoffers", produces = MediaType.APPLICATION_JSON_VALUE)  
    public  List<Offers> listOffers(
    		HttpServletRequest request, HttpServletResponse response) { 
    	List<Offers> all = quoteOfferService.listAllOffers();
    	all = removeManySideParentFromOffer(all);
    	return all;
    }
    
    
    @ResponseBody 
    @GetMapping(value = "/getquote", produces = MediaType.APPLICATION_JSON_VALUE)  
    public  Quotes getQuote ( 
    		HttpServletRequest request, HttpServletResponse response) 
    				throws JsonMappingException, JsonParseException, IOException {   	
    	String owner = request.getParameter("owner") == null? 
    			DEFAULT_OWNER : request.getParameter("owner");
    	Integer  reqNo = Integer.valueOf(request.getParameter("request_no"));
    	Quotes all = quoteOfferService.getQuoteById(new QuotesKeys(reqNo, owner));
    	List<Quotes> alls = new ArrayList<Quotes>();
    	alls.add(all);
    	alls = removeManySideParent (alls);
    	return alls.get(0);
    }    

    
    @DeleteMapping (value ="/deletequote")
    public ResponseEntity<String>  delete(HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
    	Integer reqNo = Integer.valueOf(request.getParameter("request_no"));
    	String owner = request.getParameter("owner") == null? 
    			DEFAULT_OWNER : request.getParameter("owner");	
    
        quoteOfferService.deleteQuote(new QuotesKeys(reqNo, owner));
        // Response body must be json type even empty
	    JSONObject resp = new JSONObject();  	
    	HttpHeaders headers = new HttpHeaders();

        headers.add("Access-Control-Expose-Headers", "*");
    	headers.add("message", messageResource.getMessage(
    			"quote.delete.respmessage", null, Locale.ENGLISH));

    	return new ResponseEntity<String>(
    				  resp.toString(), headers,
    			      HttpStatus.OK);    	
    } 
    
    // Save posted either old(update) or new(created) quote
    @PostMapping(value ="/savequote", consumes= "application/json") 
    public  ResponseEntity<String>  createQuotes(@RequestBody Quotes quotes, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception { 	
    	
	    String action = request.getHeader("reqaction");
    	Quotes qts = null;
    	/**
    	 * 1. new/save is based on Quote. Old quote may have new Offers.
    	 * 
    	 * 2. For error thrown, let server to return it as 
    	 * HttpErrorResponse to Angular
    	 */
    	if (action != null && action.contentEquals("new"))  {
    		qts = quoteOfferService.createQuote(quotes);
    	}
        else if (action != null && action.contentEquals("save")) {
        	qts = quoteOfferService.updateWholeQuote(quotes);
        }
    	
    	List<Quotes> alls = new ArrayList<Quotes>();
    	alls.add(qts);
    	qts = removeManySideParent (alls).get(0);
    	
    	ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString Response body
        String jsonString = mapper.writeValueAsString(qts);
    	HttpHeaders headers = new HttpHeaders();

        //For client side to add number into new quote
    	if (action != null && action.contentEquals("new") && qts != null)
    		headers.add("new_request_no", String.valueOf(qts.getRequest_no()));
        headers.add("Access-Control-Expose-Headers", "*");
    	headers.add("message", messageResource.getMessage(
    			"quote.save.respmessage", null, Locale.ENGLISH) );

    	return new ResponseEntity<String>(
    			jsonString, headers,
    			      HttpStatus.OK);    	
    }
    
	// To avoid recursive referencing, in case of bidirectional relation
    public static List<Quotes> removeManySideParent (List<Quotes> all) {
    	all.forEach( (q) -> q.getOffers().forEach((o) -> o.setQuotes(null)));  	
    	return all;
    } 
    
    public static List<Offers> removeManySideParentFromOffer (List<Offers> all) {
    	all.forEach( (q) -> q.setQuotes(null));  	
    	return all;
    } 
}
