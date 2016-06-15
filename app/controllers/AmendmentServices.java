package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.JSONObject;
import org.w3c.dom.Document;

import database.Util;
import database.XMLWriterUriTemplate;
import jaxb.XMLValidation;
import play.exceptions.JavaExecutionException;
import play.mvc.Controller;
import rs.ac.uns.ftn.amandman.Amandman;
import util.FileUtil;
import xquery.XMLReader;

public class AmendmentServices extends Controller {

    public static void saveAmendment(){
    	
    	System.out.println("SAVE Amendment");
    	String requestBody = params.get("body");
   
    	JSONObject obj = new JSONObject(requestBody);
    	try{
    		String text =obj.getString("text");
    		
        	try {
    			FileUtil.writeFile(Application.projectPath+"/XML2016/data/temp.xml", text);
    		} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        
        	XMLValidation isValid = new XMLValidation();
        	boolean xmlValid = isValid.test(Application.projectPath+"/XML2016/data/amandman.xsd");
        	if(xmlValid)
        		System.out.println("XML JE VALIDAN");
        	else 
        		renderJSON(new JSONObject("{'error':'XML dokument nije validan.'}"));

        	if(xmlValid){
        		try {
    				XMLWriterUriTemplate.run(Util.loadProperties());
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}    		
    	}catch(JavaExecutionException ex){
    		renderJSON((new JSONObject("{'error':'Morate uneti tekst!'}")));
    	}

	}

    public static void getAmendment(){
    	System.out.println("GET Amandman");
    }
	
	public static void changeStateOfAct() {
		String actNum = "8505319148387349153";
		String state = "nacelo";
		
		try {
			Document doc = XMLReader.run(Util.loadProperties(), "/acts/8505319148387349153.xml");
			JAXBContext context;
			try {
				context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				Amandman amandman = (Amandman) unmarshaller.unmarshal(doc);
				System.out.println("prosao");
				//amandman.setStatus(state);
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
