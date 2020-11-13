package com.springbootjpaangular2.domain;

import java.io.StringReader;
import java.util.Collection;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; //DocumentBuilder.*; //stream.XMLStreamReader;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.*;
import javax.xml.ws.*;

public class Folders {
    public static Collection<String> folderNames(String xml, char startingLetter) throws Exception {
    	LinkedList link = new LinkedList();
    	
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	List<String> ls = new ArrayList<String>();
    	String start = String.valueOf((startingLetter));
        
        org.w3c.dom.Document doc = builder.parse(new InputSource(
					new StringReader(xml.trim().toLowerCase())));
        
        NodeList nl = doc.getElementsByTagName("folder");
        
        for (int i=0; i < nl.getLength(); i++) {
        	NamedNodeMap  np  = nl.item(i).getAttributes();
        	
        	Node node = np.getNamedItem("name");
        	if (node.getNodeValue() != null && node.getNodeValue().startsWith(start)) {
        		ls.add(node.getNodeValue());
        	}
        		
        }
    	
    	 return ls;
    }
    /** 
    public static Collection<String> folderNames2(String xml, char startingLetter) throws Exception {
    	LinkedList link = new LinkedList();
    	
    	SAXParserFactory  saf  = SAXParserFactory.newInstance();
    	List<String> ls = new ArrayList<String>();
    	String start = String.valueOf((startingLetter));
        
        SAXParser sap = saf.newSAXParser();  
        sap.parse(xml, new Folders.MyHandler());
        
       
    	
    	 return ls;
    }
    
    
    static class Folder {
    	
    	
    }
    
   
    static class MyHandler extends DefaultHandler {

    	// List to hold folders object
    	private List<folder> empList = null;
    	private folder emp = null;
    	private StringBuilder data = null;

    	// getter method for folder list
    	public List<folder> getEmpList() {
    		return empList;
    	}

    	boolean bAge = false;
    	boolean bName = false;
    	boolean bGender = false;
    	boolean bRole = false;

    	@Override
    	public void startElement(String uri, String localName, 
    			String qName, Attributes attributes)
    					throws SAXException {

    		if (qName.equalsIgnoreCase("folder")) {
    			// create a new folder and put it in Map
    			String id = attributes.getValue("id");
    			// initialize folder object and set id attribute
    			emp = new folder();
    			emp.setId(Integer.parseInt(id));
    			// initialize list
    			if (empList == null)
    				empList = new ArrayList<>();
    		} 
    		// create the data container
    		data = new StringBuilder();
    	}

    	@Override
    	public void endElement(String uri, String localName, String qName) throws SAXException {
    		if (bAge) {
    			// age element, set folder age
    			emp.setAge(Integer.parseInt(data.toString()));
    			bAge = false;
    		} 
    		
    		if (qName.equalsIgnoreCase("folder")) {
    			// add folder object to list
    			empList.add(emp);
    		}
    	}

    	@Override
    	public void characters(char ch[], int start, int length) throws SAXException {
    		data.append(new String(ch, start, length));
    	}
    } **/
    
    
    public static void main(String[] args) throws Exception {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<folder name=\"c\">" +
                    "<folder name=\"program files\">" +
                        "<folder name=\"uninstall information\" />" +
                    "</folder>" +
                    "<folder name=\"users\" />" +
                "</folder>";

        Collection<String> names = folderNames(xml, 'u');
        for(String name: names)
            System.out.println(name);
    }
}