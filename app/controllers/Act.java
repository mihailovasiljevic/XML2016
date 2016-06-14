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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.io.marker.XMLReadHandle;
import com.marklogic.client.query.ExtractedResult;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.util.EditableNamespaceContext;

import database.Util;
import database.XMLWriterUriTemplate;
import jaxb.XMLValidation;
import play.mvc.Controller;
import play.mvc.Http;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;
import xquery.XMLReader;

public class Act extends Controller {
	private static String collectionName;
	private static DatabaseClient client;

	public static void listActs() {

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
			queryDefinition.setCollections("/acts");

			SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());

			// Serialize search results to the standard output
			MatchDocumentSummary matches[] = results.getMatchResults();

			MatchDocumentSummary result = null;
			MatchLocation locations[];
			String text;
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
			for (int i = 0; i < matches.length; i++) {
				result = matches[i];
				System.out.println((i + 1) + ". RESULT DETAILS: ");
				System.out.println("Result URI: " + result.getUri());

				Document doc = database.XMLReader.run(Util.loadProperties(), result.getUri());
				JAXBContext context;
				context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				Propis propis = (Propis) unmarshaller.unmarshal(doc);
				System.out.println("prosao");
				String name = propis.getNaziv();
				map.clear();
				map.put("name", name);
				map.put("uri", result.getUri());
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

	public static void getAct(String uri) {
		// String bodyParams = params.get("body");
		// String url = Http.Request.current().path;
		String docUri = "/acts/" + uri + ".xml";
		Document doc;
		try {
			doc = database.XMLReader.run(Util.loadProperties(), docUri);
			JAXBContext context;
			context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Propis propis = (Propis) unmarshaller.unmarshal(doc);
			System.out.println("prosao");
			renderJSON(propis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateAct(String uri) {
		String docUri = "/acts/" + uri + ".xml";
		try {
			client = DatabaseClientFactory.newClient(Util.loadProperties().host, Util.loadProperties().port, Util.loadProperties().database, Util.loadProperties().user, Util.loadProperties().password,
					Util.loadProperties().authType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Define a URI value for a document.
		String docId = "/acts/15982287759751844109.xml";

		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("b", "http://www.ftn.uns.ac.rs/pravniAkt");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Creating an XML patch
		/*
		 * <b:book category="TEST"> <b:title lang=\"en\">Test book</b:title>
		 * <b:author>Test Author</b:author> <b:year>2016</b:year>
		 * <b:price>59.99</b:price> </b:book>
		 */
		String patch = "\t<b:status>nacelo</b:status>\n";

		// Defining XPath context
		String contextXPath1 = "/b:propis/b:status";

		patchBuilder.replaceFragment(contextXPath1, patch);
		DocumentPatchHandle patchHandle = patchBuilder.build();

		System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);

		// Release the client
		client.release();
	}
	
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
    		renderJSON(new JSONObject("{'error':'XML dokument nije validan.'}"));

    	if(xmlValid){
    		try {
				XMLWriterUriTemplate.run(Util.loadProperties());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}

}
