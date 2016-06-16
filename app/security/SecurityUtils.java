package security;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.TextDocumentManager;
import com.marklogic.client.io.StringHandle;

import controllers.Application;
import database.Util;
import database.Util.ConnectionProperties;
import rs.ac.uns.ftn.pravniakt.Propis;

public class SecurityUtils {
	public static void addTimestampAndNumberAct(){
		
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Propis propis = (Propis) unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
			
			// Izmena nad objektnim modelom dodavanjem novog odseka
			Date vreme = new Date();
			propis.setVremeFormiranja(vreme.toString());
			
			// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// Podešavanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			
			
			/*INKREMENTACIJA REDNOG BROJA
			 * 
			 */
			DatabaseClient client;
			ConnectionProperties props = null;
			try {
				props = Util.loadProperties();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Initialize the database client
			if (props.database.equals("")) {
				System.out.println("[INFO] Using default database.");
				client = DatabaseClientFactory.newClient(props.host, props.port, props.user, props.password, props.authType);
			} else {
				System.out.println("[INFO] Using \"" + props.database + "\" database.");
				client = DatabaseClientFactory.newClient(props.host, props.port, props.database, props.user, props.password, props.authType);
			}
					
			// Create a document manager to work with text files.
			TextDocumentManager docManager = client.newTextDocumentManager();
			
			// Define a URI value for a document.
			String docId = "/example/rbr.txt";
			
			// Create a handle to hold string content.
			StringHandle handle = new StringHandle();
			
			// Assign some content
			StringHandle content = new StringHandle();
			docManager.read(docId,content);
			System.out.println(content);	
		    
			propis.setRedniBroj(content.get());
			
			Integer rbr = Integer.parseInt(content.get());
			rbr++;
			handle.set(rbr.toString());
			
			// Write the document (content from handle) to the database
			docManager.write(docId, handle);

			client.release();
			
			// Release the client
			
			// Umesto System.out-a, može se koristiti FileOutputStream
			marshaller.marshal(propis, new File(Application.projectPath+"/XML2016/data/temp.xml"));
			
			
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

}
