package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentDescriptor;
import com.marklogic.client.document.DocumentUriTemplate;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;

import controllers.Application;
import database.Util.ConnectionProperties;
import rs.ac.uns.ftn.pravniakt.Propis;

/**
 * 
 * [PRIMER 2]
 * 
 * Primer demonstrira automatsko generisanje document URI-ja na osnovu zadatog
 * pattern-a uz pomoć DocumentDescriptor klase.
 * 
 * Nakon izvršavanja primera pristupiti REST API-ju otvaranjem sledećeg URL-a
 * direktno iz browser-a:
 *  
 * http://{host}:8000/v1/documents?database={database}&uri=/example/xquery/{document URI}.xml
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
public class XMLWriterUriTemplate {

	private static DatabaseClient client;
	
	public static String run(ConnectionProperties props,String collectionName) throws FileNotFoundException {
		
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
		
		// Insert a document with generated URI (specifying the suffix and prefix)
		DocumentUriTemplate template = xmlManager.newDocumentUriTemplate("xml");
		template.setDirectory("/"+collectionName+"/");
		
		
		// Create an input stream handle to hold XML content.
		InputStreamHandle handle = new InputStreamHandle(new FileInputStream(Application.projectPath+"/XML2016/data/temp.xml"));
		
		
		DocumentMetadataHandle metadata = new DocumentMetadataHandle();
		metadata.getCollections().add("/"+collectionName);
		
		// Write the document to the database
		System.out.println("[INFO] Inserting \"" + template.getDirectory() + "\" to \"" + props.database + "\" database.");
		//DocumentDescriptor desc = xmlManager.create(template, metadata, handle);
		// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
		javax.xml.bind.JAXBContext context;
		Propis propis = null;
		try {
			context = javax.xml.bind.JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			javax.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();
			java.io.File tmp = new java.io.File(Application.projectPath+"/XML2016/data/temp.xml");
			propis = (Propis) unmarshaller.unmarshal(tmp);
			xmlManager.write("/acts/"+propis.getOznaka()+".xml", metadata,handle);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
		// Write the document to the database
		
		
		//System.out.println("[INFO] Generated URI: " + desc.getUri());
		System.out.print("[INFO] Verify the content at: ");
		System.out.println("http://" + props.host + ":8000/v1/documents?database=" + props.database + "&uri=" + propis.getOznaka());
		
		// Release the client
		client.release();
		
		System.out.println("[INFO] End.");
		return "/acts/"+propis.getOznaka()+".xml";
	}


}
