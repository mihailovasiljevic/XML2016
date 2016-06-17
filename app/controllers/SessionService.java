package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;

import database.Util;
import database.XMLWriterUriTemplate;
import jaxb.XMLValidation;
import play.mvc.Controller;
import rs.ac.uns.ftn.amandman.Amandman;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;

public class SessionService extends Controller{
	
	private static String collectionName;
	private static DatabaseClient client;
	private final static String COLLECTION = "/amendments";
	
	public static void vote(String uri){
    	String body = params.get("body");
    	
    	if(body!=null){
    		JSONObject obj = new JSONObject(body);
    		try{
    			int glasaliZa = obj.getInt("glasaliZa");
    			int glasaliProtiv = obj.getInt("glasaliProtiv");
    			try{
    				
    				String status = "";
    				
    				
    				if(glasaliZa > glasaliProtiv){
    					status = "nacelo";
    				}else{
    					status = "odbijen";
    				}
    				
    				Propis propis = xquery.XMLReader.getPropis("/acts/"+uri+".xml");
    				if(propis.getBrojAmandmana() > 0)
    					propis.setStatus(status);
    				else
    					propis.setStatus("celina");
    				try{
    					writePropis(propis);
    					if(propis.getStatus().equals("nacelo"))
    						renderJSON(new JSONObject("{'success':'NACELO'}"));
    					else if(propis.getStatus().equals("odbijen"))
    						renderJSON(new JSONObject("{'success':'ODBIJEN'}"));
    					else
    						renderJSON(new JSONObject("{'success':'CELINA'}"));
    				}catch(Exception ex){
    					renderJSON(new JSONObject("{'error':'Greska prilikom upisa fajla!'}"));
    				}
    			}catch (Exception e) {
    				renderJSON(new JSONObject("{'error':'Unesite iskljucivo brojeve!'}"));

				}
    		}catch (Exception e) {
				renderJSON(new JSONObject("{'error':'nema Parametara'}"));
				e.printStackTrace();
			}
    	}
	}
	
	private static void writePropis(Propis propis) throws JAXBException, FileNotFoundException{
    	JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
		Marshaller marshaller = context.createMarshaller();
		StringWriter sw = new StringWriter();
		marshaller.marshal(propis, sw);
		System.out.println(sw.toString());
		
    	FileUtil.writeFile(Application.projectPath + "/XML2016/data/temp.xml", sw.toString());
    	XMLValidation isValid = new XMLValidation();
		boolean xmlValid = isValid.test(Application.projectPath + "/XML2016/data/akt.xsd", "act");
		
		if (xmlValid){
			try {
				XMLWriterUriTemplate.run(Util.loadProperties(), "acts");
				System.out.println("[URI of saved XML]: " + propis.getOznaka());
				Act.prepareRDF("/acts/"+propis.getOznaka()+".xml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	private static void writeAmandman(Amandman amandman) throws JAXBException, FileNotFoundException{
    	JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.amandman");
		Marshaller marshaller = context.createMarshaller();
		StringWriter sw = new StringWriter();
		marshaller.marshal(amandman, sw);
		System.out.println(sw.toString());
		
    	FileUtil.writeFile(Application.projectPath + "/XML2016/data/temp.xml", sw.toString());
    	XMLValidation isValid = new XMLValidation();
		boolean xmlValid = isValid.test(Application.projectPath + "/XML2016/data/amandman.xsd", "amandman");
		
		if (xmlValid){
			try {
				XMLWriterUriTemplate.run(Util.loadProperties(), "acts");
				System.out.println("[URI of saved XML]: " + amandman.getOznaka());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 			
		}
	}
	
	public static void getAmendments(String uri){
		System.out.println("USAO U GET AMENDMENTS");
		JSONArray array = new JSONArray();
		try {
			client = DatabaseClientFactory.newClient(Util.loadProperties().host, Util.loadProperties().port,
					Util.loadProperties().database, Util.loadProperties().user, Util.loadProperties().password,
					Util.loadProperties().authType);
			// Initialize query manager
			QueryManager queryManager = client.newQueryManager();

			// Query definition is used to specify Google-style query string
			StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

			// Search within a specific collection
			queryDefinition.setCollections(COLLECTION);
			queryManager.setPageLength(500);
			SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
			
			// Serialize search results to the standard output
			MatchDocumentSummary matches[] = results.getMatchResults();
		
			MatchDocumentSummary result = null;
			MatchLocation locations[];
			String text;
			System.out.println("Length: " + matches.length);
			System.out.println("Results" + results.getTotalResults());
			for (int i = 0; i < matches.length; i++) {
				result = matches[i];
				System.out.println((i + 1) + ". RESULT DETAILS: ");
				System.out.println("Result URI: " + result.getUri());

				Document doc = xquery.XMLReader.run(Util.loadProperties(), result.getUri());
				JAXBContext context;
				context = JAXBContext.newInstance("rs.ac.uns.ftn.amandman");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				Amandman amandman = (Amandman) unmarshaller.unmarshal(doc);
				if(amandman.getOAkta() == Integer.parseInt(uri)){
					System.out.println("prosao");
					String oznaka = amandman.getOznaka()+"";
					JSONObject obj = new JSONObject();
					obj.put("oznaka", oznaka);
					obj.put("uri", prepareURI(result.getUri()));
					obj.put("oznakaAkta", amandman.getOAkta()+"");
					array.put(obj);
				}
			}
			System.out.println(array);
			JSONObject renderer = new JSONObject();
			renderer.put("lista", array);
			renderJSON(renderer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String prepareURI(String uri){
		uri = uri.replace(".", "/");
		String[] splitted = uri.split("/");
		System.out.println(splitted[2]);

		return splitted[2];
	}
	
	public static void amendmentVote(String propisURI, String amandmanURI){
    	String body = params.get("body");
    	
    	if(body!=null){
    		JSONObject obj = new JSONObject(body);
    		try{
    			int glasaliZa = obj.getInt("glasaliZa");
    			int glasaliProtiv = obj.getInt("glasaliProtiv");
    			try{
    				
    				String status = "";
    				
    				
    				if(glasaliZa > glasaliProtiv){
    					status = "usvojen";
    				}else{
    					status = "odbijen";
    				}
    				Amandman amandman = xquery.XMLReader.getAmandman("/amendments/"+amandmanURI+".xml");
    				amandman.setStatus(status);
    				
    				Propis propis = xquery.XMLReader.getPropis("/acts/"+propisURI+".xml");
    				propis.setBrojAmandmana(propis.getBrojAmandmana() - 1);
    				try{
    					writeAmandman(amandman);
        				try{
        					
        					
        					if(propis.getBrojAmandmana() == 0){
        						propis.setStatus("celina");
        						writePropis(propis);
        					}else{
        						writePropis(propis);
        					}
        					
        					renderJSON(new JSONObject("{'success':'Uspeh'}"));
        				}catch(Exception ex){
        					renderJSON(new JSONObject("{'error':'Greska prilikom upisa fajla!'}"));
        				}
    				}catch(Exception ex){
    					renderJSON(new JSONObject("{'error':'Greska prilikom upisa fajla amandmana!'}"));
    				}


    			}catch (Exception e) {
    				renderJSON(new JSONObject("{'error':'Unesite iskljucivo brojeve!'}"));

				}
    		}catch (Exception e) {
				renderJSON(new JSONObject("{'error':'nema Parametara'}"));
				e.printStackTrace();
			}
    	}
	}
	
}
