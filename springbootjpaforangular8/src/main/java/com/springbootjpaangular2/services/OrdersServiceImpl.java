package com.springbootjpaangular2.services;

import com.springbootjpaangular2.domain.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;


/** 
 * @author lyi
 * 08/2020
 */
@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    
	@Autowired 
	EntityManagerFactory factory;
    
	
	@Override  
	public EntityManagerFactory getFactory () {
		return this.factory;
	}
		
		
    @Override
    public List<Orders> listAllOrders() {
    	EntityManager em = factory.createEntityManager(); 
    	List<Orders> ords = em.createQuery("SELECT p from Orders p", 
    			Orders.class).getResultList();
    	em.close();        
        
    	return ords;
    }
   

    @Override
    public Orders getOrderById(Integer id) {
    	EntityManager em = factory.createEntityManager();   	
    	Orders pro = em.find(Orders.class, id);
	    
	    em.close();
        return pro;
    }

    
    @Override
    public Orders createOrder(Orders order) { 
    	EntityManager em = factory.createEntityManager();
    	EntityTransaction trns = em.getTransaction();
    	
    	 trns.begin();
         em.persist(order);         
         trns.commit(); 
         em.close(); 
        
        return order;  
    }
    
    
    @Override
    public List<Offers> retrieveOffers() {
    	EntityManager em = factory.createEntityManager(); 
    	List<Offers> ofs = em.createQuery("SELECT p from Offers p", 
    			Offers.class).getResultList();
    	em.close();        
        
    	return ofs;
    }
    
    
    @Override
    public Orders updateWholeOrder(Orders order)  throws Exception {
    	EntityManager em = factory.createEntityManager();
    	Integer orderNo = order.getOrder_no();
    	String owner =  order.getOwner();
    	Integer offerNo =  order.getOffer_no();
    	
    	EntityTransaction trns = em.getTransaction();
    	
    	trns.begin();      	
    	Orders ords = em.find(Orders.class, orderNo);  
		Orders.setAllFieldValue(order, ords); 
		
		order.setOrder_no(orderNo);
		order.setOffer_no(offerNo);
		order.setOwner(owner);
		
		trns.commit();
		
        em.close();          
        return ords;  
    }

  
    @Override
    public void deleteOrder(Integer id) {
    	EntityManager em = factory.createEntityManager(); 
    	EntityTransaction trns = em.getTransaction();
    	
    	trns.begin();
    	Orders ord = em.find(Orders.class, id);
    	
    	em.remove(ord);// in remove queue but not detached
    	em.flush(); 
    	em.clear(); 
        trns.commit(); 
        em.close(); 
    }
}
