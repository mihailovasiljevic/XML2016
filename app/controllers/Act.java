package controllers;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.map.HashedMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.DOMHandle;
import com.marklogic.client.io.FileHandle;
import com.marklogic.client.io.Format;
import com.marklogic.client.io.JacksonHandle;
import com.marklogic.client.io.SearchHandle;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.io.marker.XMLReadHandle;
import com.marklogic.client.query.ExtractedResult;
import com.marklogic.client.query.MatchDocumentSummary;
import com.marklogic.client.query.MatchLocation;
import com.marklogic.client.query.MatchSnippet;
import com.marklogic.client.query.QueryManager;
import com.marklogic.client.query.SearchResults;
import com.marklogic.client.query.StringQueryDefinition;
import com.marklogic.client.semantics.GraphManager;
import com.marklogic.client.semantics.RDFMimeTypes;
import com.marklogic.client.semantics.SPARQLMimeTypes;
import com.marklogic.client.semantics.SPARQLQueryDefinition;
import com.marklogic.client.semantics.SPARQLQueryManager;
import com.marklogic.client.util.EditableNamespaceContext;

import database.Util;
import database.XMLWriterUriTemplate;
import grddl.MetadataExtractor;
import jaxb.XMLValidation;
import play.Play;
import play.exceptions.JavaExecutionException;
import play.mvc.Controller;
import play.mvc.Http;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;
import xquery.XMLReader;
import xslfo.XSLFOTransformer;
import xslfo.XSLTransformer;

public class Act extends Controller {
	private static String collectionName;
	private static DatabaseClient client;
	private final static String COLLECTION = "/acts";
	private static TransformerFactory transformerFactory;
	private static final String SPARQL_NAMED_GRAPH_URI = "/acts/sparql/metadata";
	static {
		transformerFactory = TransformerFactory.newInstance();
	}

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
				context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				Propis propis = (Propis) unmarshaller.unmarshal(doc);
				System.out.println("prosao");
				String name = propis.getNaziv();
				String status = propis.getStatus();
				System.out.println("Status: " + status);
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("name", name);
				map.put("uri", prepareURI(result.getUri()));
				map.put("status", status);
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
			doc = xquery.XMLReader.run(Util.loadProperties(), docUri);
			JAXBContext context;
			context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Propis propis = (Propis) unmarshaller.unmarshal(doc);
			System.out.println("prosao");
			try{
				renderJSON(propis);
			}catch (Exception e) {
				renderJSON(new JSONObject("{'naziv':'"+propis.getNaziv()+"'}"));
			}
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

	private static String prepareURI(String uri) {
		uri = uri.replace(".", "/");
		String[] splitted = uri.split("/");
		System.out.println(splitted[2]);

		return splitted[2];
	}

	public static void updateAct(String uri) {
		String docUri = "/acts/" + uri + ".xml";
		try {
			client = DatabaseClientFactory.newClient(Util.loadProperties().host, Util.loadProperties().port,
					Util.loadProperties().database, Util.loadProperties().user, Util.loadProperties().password,
					Util.loadProperties().authType);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Define a URI value for a document.
		String docId = docUri;

		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("b", "http://www.ftn.uns.ac.rs/pravniAkt");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		String patch = "\t<b:status>povucen</b:status>\n";

		// Defining XPath context
		String contextXPath1 = "/b:propis/b:status";

		patchBuilder.replaceFragment(contextXPath1, patch);
		DocumentPatchHandle patchHandle = patchBuilder.build();

		System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);

		// Release the client
		client.release();
	}

	public static void saveAct() {

		System.out.println("SAVE ACT");
		String requestBody = params.get("body");

		JSONObject obj = new JSONObject(requestBody);
		try {
			String text = obj.getString("text");

			try {
				FileUtil.writeFile(Application.projectPath + "/XML2016/data/temp.xml", text);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			XMLValidation isValid = new XMLValidation();
			boolean xmlValid = isValid.test(Application.projectPath + "/XML2016/data/akt.xsd", "act");
			if (xmlValid)
				System.out.println("XML JE VALIDAN");
			else {
				renderJSON(new JSONObject("{'error':'XML dokument nije validan.'}"));
				return;
			}

			if (xmlValid) {
				try {
					String uri = XMLWriterUriTemplate.run(Util.loadProperties(), "acts");
					System.out.println("[URI of saved XML]: " + uri);
					prepareRDF(uri);
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
		} catch (JavaExecutionException ex) {
			renderJSON((new JSONObject("{'error':'Morate uneti tekst!'}")));
		}

	}

	public static void search() {
		System.out.println("USAO U SEARCH!");
		String body = params.get("body");

		JSONObject obj = new JSONObject(body);
		String criteria = obj.getString("criteria");
		System.out.println("Criteria: " + criteria);
		ArrayList<LinkedHashMap<String, String>> documentsInfo = new ArrayList<LinkedHashMap<String, String>>();
		try {
			client = DatabaseClientFactory.newClient(Util.loadProperties().host, Util.loadProperties().port,
					Util.loadProperties().database, Util.loadProperties().user, Util.loadProperties().password,
					Util.loadProperties().authType);

			// Initialize query manager
			QueryManager queryManager = client.newQueryManager();

			// Query definition is used to specify Google-style query string
			StringQueryDefinition queryDefinition = queryManager.newStringDefinition();

			// Set the criteria
			queryDefinition.setCriteria(criteria);

			// Search within a specific collection
			queryDefinition.setCollections(COLLECTION);

			System.out.println("Pokusava SEARCH!");
			// Perform search
			SearchHandle results = queryManager.search(queryDefinition, new SearchHandle());
			System.out.println("ZAVRSIO SEARCH!");
			MatchDocumentSummary matches[] = results.getMatchResults();

			MatchDocumentSummary result = null;
			MatchLocation locations[];
			String text;
			for (int i = 0; i < matches.length; i++) {
				result = matches[i];
				System.out.println((i + 1) + ". RESULT DETAILS: ");
				System.out.println("Result URI: " + result.getUri());

				Document doc = xquery.XMLReader.run(Util.loadProperties(), result.getUri());
				JAXBContext context;
				context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
				Unmarshaller unmarshaller = context.createUnmarshaller();
				Propis propis = (Propis) unmarshaller.unmarshal(doc);

				String name = propis.getNaziv();

				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
				map.put("uri", prepareURI(result.getUri()));
				map.put("name", name);
				map.put("status", propis.getStatus());
				map.put("criteria", criteria);
				map.put("fitness", result.getFitness() + "");
				documentsInfo.add(map);
			}

			if (documentsInfo.size() == 0) {
				renderJSON(new JSONObject("{'error':'Za dati kriterijume nema rezultata pretrage.'}"));
			} else {
				JSONArray array = new JSONArray();
				for (LinkedHashMap<String, String> hm : documentsInfo) {
					JSONObject object = new JSONObject();
					for (String key : hm.keySet()) {
						object.put(key, hm.get(key));
					}
					array.put(object);
				}
				System.out.println(array);
				System.out.println(new JSONObject("{'data':" + array + "}"));
				renderJSON(array);
			}

			// Release the client
			client.release();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

	/**
	 * Serializes DOM tree to an arbitrary OutputStream.
	 *
	 * @param node
	 *            a node to be serialized
	 * @param out
	 *            an output stream to write the serialized DOM representation to
	 * 
	 */
	private static void transform(Node node, OutputStream out) {
		try {

			// Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
			Transformer transformer = transformerFactory.newTransformer();

			// Indentacija serijalizovanog izlaza
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			// Nad "source" objektom (DOM stablo) vrši se transformacija
			DOMSource source = new DOMSource(node);

			// Rezultujući stream (argument metode)
			StreamResult result = new StreamResult(out);

			// Poziv metode koja vrši opisanu transformaciju
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	public static void pdf(String uri) {

		String docId = "/acts/" + uri + ".xml";
		String text = XMLReader.getPropisText(docId);

		try {
			XSLFOTransformer trans = new XSLFOTransformer();
			String path = trans.transform(text, uri);
			JSONObject obj = new JSONObject();
			obj.put("path", path);
			renderJSON(obj);

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("pdf is genereted. It can be found in \"gen\" directory.");
	}

	public static void xhtml(String uri) {
		String docId = "/acts/" + uri + ".xml";
		String text = XMLReader.getPropisText(docId);
		String response = XSLTransformer.transform(text);

		HashMap<String, String> map = new HashMap<String, String>();
		JSONObject obj = new JSONObject();
		obj.put("html", response);

		renderJSON(obj);
		System.out.println("xhtml is genereted.");
	}

	public static void sendingAct(String uri) {
		redirect("http://localhost:7000/sendingAct/" + uri);

	}

	private static void prepareRDF(String uri) throws IOException, SAXException, TransformerException {
		// String docUri = "/acts/" + uri + ".xml";

		System.out.println("[INFO] " + Act.class.getSimpleName());

		client = DatabaseClientFactory.newClient(Util.loadProperties().host, Util.loadProperties().port,
				Util.loadProperties().database, Util.loadProperties().user, Util.loadProperties().password,
				Util.loadProperties().authType);

		// Create a document manager to work with XML files.
		GraphManager graphManager = client.newGraphManager();

		// Set the default media type (RDF/XML)
		graphManager.setDefaultMimetype(RDFMimeTypes.RDFXML);

		// Referencing XML file with RDF data in attributes
		String xmlFilePath = xquery.XMLReader.getPropisText(uri);

		String rdfFilePath = "/gen/acts/act_" + prepareURI(uri) + ".rdf";
		System.out.println("UPIS PROSAO!");

		// Automatic extraction of RDF triples from XML file
		MetadataExtractor metadataExtractor = new MetadataExtractor();

		metadataExtractor.extractMetadata(new FileInputStream(new File(Play.applicationPath + "/data/temp.xml")),
				new FileOutputStream(new File(Play.applicationPath + rdfFilePath)));

		// A handle to hold the RDF content.
		FileHandle rdfFileHandle = new FileHandle(new File(Play.applicationPath + rdfFilePath))
				.withMimetype(RDFMimeTypes.RDFXML);

		// Writing the named graph
		System.out.println("[INFO] Overwriting triples to a named graph \"" + SPARQL_NAMED_GRAPH_URI + "\".");
		graphManager.merge(SPARQL_NAMED_GRAPH_URI, rdfFileHandle);

		// Read the triples from the named graph
		System.out.println();
		System.out.println("[INFO] Retrieving triples from RDF store.");
		System.out.println("[INFO] Using \"" + SPARQL_NAMED_GRAPH_URI + "\" named graph.");

		// Define a DOM handle instance to hold the results
		DOMHandle domHandle = new DOMHandle();

		// Retrieve RDF triplets in format (RDF/XML) other than default
		graphManager.read(SPARQL_NAMED_GRAPH_URI, domHandle).withMimetype(RDFMimeTypes.RDFXML);

		// Serialize document to the standard output stream
		System.out.println("[INFO] Rendering triples as \"application/rdf+xml\".");
		util.DOMUtil.transform(domHandle.get(), System.out);

		// Release the client
		client.release();

		System.out.println("[INFO] End.");

	}

	private static void handleResults(JacksonHandle resultsHandle) {

		JsonNode tuples = resultsHandle.get().path("results").path("bindings");
		Iterator<JsonNode> nodes;

		for (JsonNode row : tuples) {
			nodes = row.iterator();

			while (nodes.hasNext()) {
				System.out.println("\t" + nodes.next().path("value").asText());
			}
			System.out.println();
		}
	}
	
	private static JSONObject findAktById(String path){
		JSONObject retVal = new JSONObject();
		
		path = path.substring(42);
		
		System.out.println("[PATH]: " + path);
		return retVal;
		
	}
	
	public static void searchByMetaData() {
		String body = params.get("body");


		String query = "PREFIX xs:     <http://www.w3.org/2001/XMLSchema#>\r\nSELECT * FROM </acts/sparql/metadata>\r\nWHERE {\r\n";
		query += "\t?act <http://ftn.uns.ac.rs/pravniAkt/predicate/datumKreiranja> ?datumKreiranja .\r\n\t?act <http://ftn.uns.ac.rs/pravniAkt/predicate/datumUsvajanjaUNacelu> ?datumUsvajanjaUNacelu .\r\n  \t?act <http://ftn.uns.ac.rs/pravniAkt/predicate/datumUsvajanjaUCelosti> ?datumUsvajanjaUCelosti .\r\n  \t?act <http://ftn.uns.ac.rs/pravniAkt/predicate/glasaliZa> ?glasaliZa .\r\n  \t?act <http://ftn.uns.ac.rs/pravniAkt/predicate/glasaliProtiv> ?glasaliProtiv .";
		query += "FILTER ( ";
		
		if (body != null) {
			System.out.println("[body]: " + body);
			String datumKreiranjaOd = null;
			String datumKreiranjaDo = null;
			String datumUsvajanjaUNaceluOd = null;
			String datumUsvajanjaUNaceluDo = null;
			String datumUsvajanjaUCelostiOd = null;
			String datumUsvajanjaUCelostiDo = null;
			String glasaliZaOd = null;
			String glasaliZaDo = null;
			String glasaliProtivOd = null;
			String glasaliProtivDo = null;
			JSONObject obj = new JSONObject(body);
			
			try{
				datumKreiranjaOd = obj.getString("datumKreiranjaOd");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				 datumKreiranjaDo = obj.getString("datumKreiranjaDo");
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			try{
				datumUsvajanjaUNaceluOd = obj.getString("datumUsvajanjaUNaceluOd");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				datumUsvajanjaUNaceluDo = obj.getString("datumUsvajanjaUNaceluDo");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				datumUsvajanjaUCelostiOd = obj.getString("datumUsvajanjaUCelostiOd");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				datumUsvajanjaUCelostiDo = obj.getString("datumUsvajanjaUCelostiDo");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				glasaliZaOd = obj.getString("glasaliZaOd");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				glasaliZaDo = obj.getString("glasaliZaDo");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				glasaliProtivOd = obj.getString("glasaliProtivOd");
			}catch (Exception e) {
				// TODO: handle exception
			}
			try{
				glasaliProtivDo = obj.getString("glasaliProtivDo");
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			boolean queryStarted = false;
			if (datumKreiranjaOd != null || datumKreiranjaDo != null || datumUsvajanjaUNaceluOd != null
					|| datumUsvajanjaUNaceluDo != null || datumUsvajanjaUCelostiOd != null
					|| datumUsvajanjaUCelostiDo != null || glasaliZaOd != null || glasaliZaDo != null
					|| glasaliProtivOd != null || glasaliProtivDo != null) {
				if (datumKreiranjaOd != null) {
					query+="?datumKreiranja >= \""+datumKreiranjaOd+"\"^^xs:date";
					queryStarted = true;
				}
				if (datumKreiranjaDo != null) {
					if(queryStarted){
						query+=" && ?datumKreiranja <= \""+datumKreiranjaDo+"\"^^xs:date";
					}else{
						query+=" ?datumKreiranja <= \""+datumKreiranjaDo+"\"^^xs:date";
						queryStarted = true;
					}
				}
				if (datumUsvajanjaUNaceluOd != null) {

					if(queryStarted){
						query+=" && ?datumUsvajanjaUNacelu >= \""+datumUsvajanjaUNaceluOd+"\"^^xs:date";
					}else{
						query+=" ?datumUsvajanjaUNacelu >= \""+datumUsvajanjaUNaceluOd+"\"^^xs:date";
						queryStarted = true;
					}
				}
				if (datumUsvajanjaUNaceluDo != null) {
					if(queryStarted){
						query+=" && ?datumUsvajanjaUNacelu <= \""+datumUsvajanjaUNaceluDo+"\"^^xs:date";
					}else{
						query+=" ?datumUsvajanjaUNacelu <= \""+datumUsvajanjaUNaceluDo+"\"^^xs:date";
						queryStarted = true;
					}
				}
				if (datumUsvajanjaUCelostiOd != null) {
					if(queryStarted){
						query+=" && ?datumUsvajanjaUCelosti >= \""+datumUsvajanjaUCelostiOd+"\"^^xs:date";
					}else{
						query+=" ?datumUsvajanjaUCelosti >= \""+datumUsvajanjaUCelostiOd+"\"^^xs:date";
						queryStarted = true;
					}
				}
				if (datumUsvajanjaUCelostiDo != null) {
					if(queryStarted){
						query+=" && ?datumUsvajanjaUCelosti <= \""+datumUsvajanjaUCelostiDo+"\"^^xs:date";
					}else{
						query+=" ?datumUsvajanjaUCelosti <= \""+datumUsvajanjaUCelostiDo+"\"^^xs:date";
						queryStarted = true;
					}
				}
				if (glasaliZaOd != null) {
					if(queryStarted){
						query+=" && ?glasaliZa >= \""+glasaliZaOd+"\"^^xs:int";
					}else{
						query+=" ?glasaliZa >= \""+glasaliZaOd+"\"^^xs:int";
						queryStarted = true;
					}
				}
				if (glasaliZaDo != null) {
					if(queryStarted){
						query+=" && ?glasaliZa <= \""+glasaliZaDo+"\"^^xs:int";
					}else{
						query+=" ?glasaliZa <= \""+glasaliZaDo+"\"^^xs:int";
						queryStarted = true;
					}
				}
				if (glasaliProtivOd != null) {
					if(queryStarted){
						query+=" && ?glasaliProtiv >= \""+glasaliProtivOd+"\"^^xs:int";
					}else{
						query+=" ?glasaliProtiv >= \""+glasaliProtivOd+"\"^^xs:int";
						queryStarted = true;
					}
				}
				if (glasaliProtivDo != null) {
					if(queryStarted){
						query+=" && ?glasaliProtiv <= \""+glasaliProtivDo+"\"^^xs:int";
					}else{
						query+=" ?glasaliProtiv <= \""+glasaliProtivDo+"\"^^xs:int";
						queryStarted = true;
					}
				}

			}
			query += ")\r\n}";
		}else{
			System.out.println("[QUERY - ERROR]: BODY je prazan" );
			return;
		}
		
		System.out.println("[QUERY]: "+ query);
		
		try {
			client = DatabaseClientFactory.newClient(
					Util.loadProperties().host, 
					Util.loadProperties().port, 
					Util.loadProperties().database, 
					Util.loadProperties().user, 
					Util.loadProperties().password, 
					Util.loadProperties().authType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		// Create a SPARQL query manager to query RDF datasets
		SPARQLQueryManager sparqlQueryManager = client.newSPARQLQueryManager();
		
		//query = "PREFIX xs:     <http://www.w3.org/2001/XMLSchema#>\r\nSELECT * FROM </acts/sparql/metadata> \r\nWHERE { ?s ?p ?o }";		
		// Initialize SPARQL query definition from an external .rq file 
		SPARQLQueryDefinition sparqlQuery = sparqlQueryManager
				.newQueryDefinition(query);
				
		// Initialize Jackson results handle
		JacksonHandle resultsHandle = new JacksonHandle();
		resultsHandle.setMimetype(SPARQLMimeTypes.SPARQL_JSON);
		

		resultsHandle = sparqlQueryManager.executeSelect(sparqlQuery, resultsHandle);
		handleResults(resultsHandle);
	
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");

	}

}
