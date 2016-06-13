package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import jaxb.XMLValidation;

import org.json.JSONObject;

import database.Util;
import database.XMLWriterUriTemplate;
import play.mvc.Controller;
import util.FileUtil;

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
    	boolean xmlValid = isValid.test(Application.projectPath+"/XML2016/data/akt.xsd");
    	if(xmlValid)
    		System.out.println("XML JE VALIDAN");
    	else 
    		System.out.println("XML NIJE VALIDAN");

    	if(xmlValid){
    		try {
				XMLWriterUriTemplate.run(Util.loadProperties());
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
		
		
		
	}
	
}
