package com.springbootjpaangular2.services;

import com.springbootjpaangular2.domain.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import io.reactivex.Observable;

/**
 * 
 * @author lyi
 * 
 */
@Service
@Transactional
public class QuoteOfferServiceImpl implements QuoteOfferService {
	private static final Logger logger = LoggerFactory.getLogger(
			QuoteOfferServiceImpl.class);
	
	// thread safe singleton, EntityManager is thread unsafe
	private final EntityManagerFactory entityManagerFactory; 

	
	@Autowired
	public QuoteOfferServiceImpl (EntityManagerFactory factory) {
		this.entityManagerFactory = factory;
	}

	
	@Override  
	public EntityManagerFactory getEntityManagerFactory () {
		return this.entityManagerFactory;
		
	}
	

	/** get, find, load are auto-committed/transaction **/
    @Override
    public List<Quotes> listAllQuotes() {
    	EntityManager em = entityManagerFactory.createEntityManager(); 
    	List<Quotes> qts = em.createQuery("SELECT p from Quotes p", 
    			Quotes.class).getResultList();
    	em.close();        
    	return qts;
    }
   

    @Override
    public Quotes getQuoteById(QuotesKeys id) {
    	EntityManager em = entityManagerFactory.createEntityManager();   	
    	Quotes qts = em.find(Quotes.class, id);	    
	    em.close();
        return qts;
    }


    @Override
    public Quotes createQuote(Quotes quote) { 
    	EntityManager em = entityManagerFactory.createEntityManager();
    	EntityTransaction trns = em.getTransaction();
    	List<Offers> offersNew = new ArrayList<Offers>();
    	
    	if (quote.getRequest_no() == null) { // should be null
	        /**
	         *  Quote and Offer cannot be created at same transaction because 
	         *  request_no/owner unknown yet. Persist(quote), then set request_no
	         *  to offer, finally flush and committed transaction.
	         *  
	         *  new ArrayList<Offers>(quote.getOffers()) for
	         *  avoiding java.util.ConcurrentModificationException.
	         *  
	         *  for ( Offers o : offers) {
	      	 *	  offers.remove(o);
	      	 *  }  	
	      	 * 	
	         *  cause error: 
	         *  at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:909)
	         *  java.util.ConcurrentModificationException: null: exception because 
	         *  you are modifying the list while iterating, which means if the Collection 
	         *  will be changed while some thread is traversing over it using iterator, 
	         *  the iterator.next() 
	         *         
	         **/
    		for (Offers o: new ArrayList<Offers>(quote.getOffers())) {
    			offersNew.add(o);
    			quote.removeOffer(o);
    		}
    	} 
    	
    	 trns.begin();
         em.persist(quote); // In managed context      
         for (Offers o: offersNew) {
        	 quote.addOffer(o); // in managed context
         }
  
         em.flush(); // send to db but not committed, 
         em.clear(); // release memory, detached
         trns.commit(); // real saved in db
         em.close(); // may be flushed and committed depends database vendor
        
        return quote;  
    }
    /** 
     * Update strategy: 
     * Copy all values (except generated keys) of offers from posted source into
     * origin target offers in context.
     * 
     * Another way is post changed fields only and reset it. 
     */
    @Override
    public Quotes updateWholeQuote(Quotes quote) throws Exception {
    	EntityManager em = entityManagerFactory.createEntityManager();
    	Integer reqNo = quote.getRequest_no();
    	String owner =  quote.getOwner();
    	
    	EntityTransaction trns = em.getTransaction();
    	
    	trns.begin();      	
    	Quotes qts = em.find(Quotes.class, new QuotesKeys(reqNo, owner));   
    	// Need remove client side deleted offers
    	List<Offers> survivedOffers = quote.getOffers().stream().filter(
    			p-> p.getOffer_no() != null).collect(Collectors.toList());
    	/**
    	 *  if managed Offers not in posted, delete it from context.
    	 */ 
		for ( Offers o: new ArrayList<Offers>(qts.getOffers())) {
			if (o == null) continue;
			
			List<Offers> t = survivedOffers.stream().filter(p -> 
				 o.getOffer_no().equals(p.getOffer_no())).collect(Collectors.toList());

			if (t.size() == 0) {
				qts.removeOffer(o);
			}
		}
		
		Quotes.setAllFieldValue(quote, qts); 
		trns.commit();
		
        em.close();          
        return qts;  
    }
    
    
    @Override
    public void deleteQuote(QuotesKeys id) {
    	EntityManager em = entityManagerFactory.createEntityManager(); 
    	EntityTransaction trns = em.getTransaction();
    	
    	trns.begin();
    	Quotes qts = em.find(Quotes.class, id);
    	
    	em.remove(qts);// in remove queue but not detached
    	em.flush(); 
    	em.clear(); 
        trns.commit(); 
        em.close(); 
    }
        
   
    @Override
    public List<Offers> listAllOffers() {
     	EntityManager em = entityManagerFactory.createEntityManager(); 
    	List<Offers> ofs = em.createQuery("SELECT p from Offers p", 
    			Offers.class).getResultList();
    	em.close();             
    	return ofs;
    }
   

    @Override
    public Offers getOfferById(OffersKeys id) {
    	EntityManager em = entityManagerFactory.createEntityManager();   	
    	Offers ofs = em.find(Offers.class, id);	    
	    em.close();
        return ofs;
    }
  
}
