package xquery;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import database.Util;
import database.Util.ConnectionProperties;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.InputStreamHandle;

import controllers.Application;

/**
 * 
 * [PRIMER 1]
 * 
 * Primer demonstrira pisanje XML dokumenta sa unapred zadatim URI-jem u
 * MarkLogic XMLDB upotrebom Java API-ja (XMLDocumentManager klase).
 * 
 * Nakon izvršavanja primera pristupiti REST API-ju otvaranjem sledećeg URL-a
 * direktno iz browser-a:
 * 
 * http://{host}:8000/v1/documents?database={database}&uri=/example/test/books.xml
 * 
 * ili kroz MarkLogic-ov Query Console na adresi:
 * 
 * http://{host}:8000/qconsole/
 * 
 * Za detaljan opis parametara MarkLogic-ovog klijentskog REST API-ja posetiti:
 * 
 * https://docs.marklogic.com/REST/client
 * 
 */
public class XMLWriter {

	private static DatabaseClient client;
	
	public static void run(ConnectionProperties props) throws FileNotFoundException {
		
		System.out.println("[INFO] " + XMLWriter.class.getSimpleName());
		
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
		String docId = "/security/users.xml";
		String testDocId = "./data/probaaa3.xml";
		
		// Create an input stream handle to hold XML content.
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(Application.projectPath+"/XML2016/xml/users.xml"));
		
		// Write the document to the database
		System.out.println("[INFO] Inserting \"" + docId + "\" to \"" + props.database + "\" database.");
		xmlManager.write(docId, handle);
		
		// Write the same document with a new id 
		System.out.println("[INFO] Inserting \"" + testDocId + "\" to \"" + props.database + "\" database.");
		//handle = new InputStreamHandle(new FileInputStream("xml/probaaa.xml"));
		//xmlManager.write(testDocId, handle);
		
		// Document deletion
	//	System.out.println("[INFO] Removing \"" + testDocId + "\" from \"" + props.database + "\" database.");
	//	xmlManager.delete(testDocId);
		
	//	System.out.println("[INFO] Verify the content at: http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + docId);
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
	}

	public static void main(String[] args) throws IOException {
		run(Util.loadProperties());
	}

}
