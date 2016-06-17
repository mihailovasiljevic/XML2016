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
import rs.ac.uns.ftn.amandman.Amandman;
import rs.ac.uns.ftn.pravniakt.Propis;

public class SecurityUtils {
	public static void addTimestampAndNumberAct(String type){
		
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			JAXBContext context=null;
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			if(type.equals("act"))
			context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			else context = JAXBContext.newInstance("rs.ac.uns.ftn.amandman");
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Date vreme = new Date();
			Propis propis = new Propis();
			Amandman amandman = new Amandman();
			if(type.equals("act")){
			propis = (Propis) unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
			propis.setVremeFormiranja(vreme.toString());
			}
			else{
				amandman = (Amandman) unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
				amandman.setVremeFormiranja(vreme.toString());
			}
			// Izmena nad objektnim modelom dodavanjem novog odseka
			
			
			
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
		    
			if(type.equals("act"))
			propis.setRedniBroj(content.get());
			else amandman.setRedniBroj(content.get());
			
			Integer rbr = Integer.parseInt(content.get());
			rbr++;
			handle.set(rbr.toString());
			
			// Write the document (content from handle) to the database
			docManager.write(docId, handle);

			client.release();
			
			// Release the client
			
			// Umesto System.out-a, može se koristiti FileOutputStream
			if(type.equals("act"))
			marshaller.marshal(propis, new File(Application.projectPath+"/XML2016/data/temp.xml"));
			else marshaller.marshal(amandman, new File(Application.projectPath+"/XML2016/data/temp.xml"));
			
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}

}
