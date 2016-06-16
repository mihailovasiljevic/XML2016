package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.JSONObject;
import org.w3c.dom.Document;

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
import play.exceptions.JavaExecutionException;
import play.mvc.Controller;
import rs.ac.uns.ftn.amandman.Amandman;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;
import xquery.XMLReader;

public class AmendmentServices extends Controller {
	
	private static String collectionName;
	private static DatabaseClient client;
	private final static String COLLECTION = "/amendments";
	
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
        	boolean xmlValid = isValid.test(Application.projectPath+"/XML2016/data/amandman.xsd","amandman");
        	if(xmlValid)
        		System.out.println("XML JE VALIDAN");
        	else 
        		renderJSON(new JSONObject("{'error':'XML dokument nije validan.'}"));

        	if(xmlValid){
        		try {
    				XMLWriterUriTemplate.run(Util.loadProperties(),"amendments");
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
	
	public static void listAmendments() {

		ArrayList<LinkedHashMap<String, String>> documentsURIs = new ArrayList<LinkedHashMap<String, String>>();
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
				System.out.println("prosao");
				String oznaka = amandman.getOznaka()+"";
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("oznaka", oznaka);
				map.put("uri", prepareURI(result.getUri()));
				documentsURIs.add(map);
			}
			System.out.println(new JSONObject(documentsURIs));
			if (documentsURIs.isEmpty())
				renderJSON(new JSONObject("{'failure':'Lista je pazna'}"));
			else
				renderJSON(documentsURIs);
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
	
}
