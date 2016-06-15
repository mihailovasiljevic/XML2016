package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import jaxb.XMLValidation;

import org.json.JSONObject;
import org.w3c.dom.Document;

import database.Util;
import database.XMLWriterUriTemplate;
import play.mvc.Controller;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;
import xquery.XMLReader;

public class ActServices extends Controller {

    public static void saveAct(){
    	
    	System.out.println("SAVE ACT");
    	String requestBody = params.get("body");
   
    	JSONObject obj = new JSONObject(requestBody);
    	String text =obj.getString("text");

    	try {
			FileUtil.writeFile(Application.projectPath+"/XML2016/data/temp.xml", text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    	XMLValidation isValid = new XMLValidation();
    	boolean xmlValid = isValid.test(Application.projectPath+"/XML2016/data/akt.xsd","akt");
    	if(xmlValid)
    		System.out.println("XML JE VALIDAN");
    	else 
    		System.out.println("XML NIJE VALIDAN");

    	if(xmlValid){
    		try {
				XMLWriterUriTemplate.run(Util.loadProperties(),"acts");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}

    public static void getAct(){
    	System.out.println("GET ACT");
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
				Propis propis = (Propis) unmarshaller.unmarshal(doc);
				System.out.println("prosao");
				propis.setStatus(state);
				
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
