package database;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

import database.Util.ConnectionProperties;

/**
 * 
 * [PRIMER 5]
 * 
 * Primer demonstrira parcijalnu izmenu XML dokumenta upotrebom
 * DocumentPatchHandle klase MarkLogic Java API-ja. Parcijalno ažuriranje XML
 * dokumenta realizuje se zadavanjem konteksta, u vidu XPath putanje, pozicije
 * izmene, zadate relativno u odnosu na kontekst, kao i nove vrednosti čvora.
 * 
 * Nakon izvršavanja primera pokrenuti prethodni primer ili pristupiti REST
 * API-ju otvaranjem sledećeg URL-a direktno iz browser-a:
 * 
 * http://{host}:8000/v1/documents?database={database}&uri=/example/books.xml
 * 
 * alternativno, upotrebom MarkLogic-ovog Query Console-a na adresi:
 * 
 * http://{host}:8000/qconsole/
 * 
 * Za detaljan opis parametara MarkLogic-ovog klijentskog REST API-ja posetiti:
 * 
 * https://docs.marklogic.com/REST/client
 * 
 */
public class XMLPartialUpdate {

	private static DatabaseClient client;
	
	public static void run(ConnectionProperties props, String patch) throws FileNotFoundException {

		//System.out.println("[INFO] " + XMLPartialUpdate.class.getSimpleName());
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		// Define a URI value for a document.
		String docId = "/example/player.xml";

		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("b", "http://www.ftn.uns.ac.rs/xpath/examples");
		namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");
		
		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		// Creating an XML patch
		/*
				<b:book category="TEST">
					<b:title lang=\"en\">Test book</b:title>
					<b:author>Test Author</b:author>
					<b:year>2016</b:year>
					<b:price>59.99</b:price>
				</b:book>
		 */
		
		// Defining XPath context
		String contextXPath2 = "/players";
		

		// Insert fragments
		
		patchBuilder.insertFragment(contextXPath2, Position.LAST_CHILD, patch);
		
		DocumentPatchHandle patchHandle = patchBuilder.build();
		
		System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);
		
		
	}
	
	public static void modifyAct(ConnectionProperties props, String path, String patch, String docId) {
		
		
		// Initialize the database client
		if (props.database.equals("")) {
			System.out.println("[INFO] Using default database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
		} else {
			System.out.println("[INFO] Using \"" + props.database + "\" database.");
			client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
		}
		
		// Create a document manager to work with XML files.
		XMLDocumentManager xmlManager = client.newXMLDocumentManager();

		
		// Defining namespace mappings
		EditableNamespaceContext namespaces = new EditableNamespaceContext();
		namespaces.put("akt", "http://www.ftn.uns.ac.rs/pravniAkt");
		//namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");
		
		// Assigning namespaces to patch builder
		DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		
		DocumentPatchHandle patchHandle = patchBuilder.build();
		
		System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
		xmlManager.patch(docId, patchHandle);
		
		patchBuilder = xmlManager.newPatchBuilder();
		patchBuilder.setNamespaces(namespaces);

		
		patchBuilder.replaceFragment(path, patch);

		patchHandle = patchBuilder.build();
		
		xmlManager.patch(docId, patchHandle);
		
		client.release();
		
	}
	

}
