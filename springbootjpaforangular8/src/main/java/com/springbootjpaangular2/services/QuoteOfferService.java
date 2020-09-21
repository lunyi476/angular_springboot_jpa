package com.springbootjpaangular2.services;


import com.springbootjpaangular2.domain.Quotes;
import com.springbootjpaangular2.domain.QuotesKeys;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import com.springbootjpaangular2.domain.Offers;
import com.springbootjpaangular2.domain.OffersKeys;


public interface QuoteOfferService {
    List<Quotes> listAllQuotes();

    // Update all fields as we don't know specific changed fields
    Quotes updateWholeQuote(Quotes quote) throws Exception;
    
    Quotes createQuote(Quotes quotes);

    void deleteQuote(QuotesKeys id);
    
    List<Offers> listAllOffers();

    Offers getOfferById(OffersKeys id);
    
    public EntityManagerFactory getFactory ();
    public void setFactory (EntityManagerFactory factory);

	Quotes getQuoteById(QuotesKeys id);
}
