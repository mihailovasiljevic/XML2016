package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import rs.ac.uns.ftn.pravniakt.Propis;
import security.EncryptKEK;
import security.KeyStoreReader;
import security.SignEnveloped;
import util.FileUtil;
import database.FileWriterReader;
import xquery.XMLReader;
import xquery.XMLWriter;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.cert.Certificate;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import jaxb.Korisnici;
import jaxb.Korisnik;
import jaxb.Marshalling;
import jaxb.XMLValidation;
import net.sf.ezmorph.ObjectMorpher;

import org.h2.constant.SysProperties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.document.DocumentPatchBuilder.Position;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

import database.XMLPartialUpdate;
import database.XMLWriterUriTemplate;
import database.Util;
import models.Player;
import models.User;

public class Application extends Controller {

	public static String projectPath;
	public static DatabaseClient client;
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<User> users = new ArrayList<User>();
//	private static  String IN_FILE = FilePaths.korisnici;
	private static  String certificate = "sgns";
	
	/** Ukoliko je potrebno koristiti sesiju to je moguce uz pomoc objekta session na sledeci nacin:
	 * session.put(key, value). Sesija se preuzima sa session.get(value). 
	 * Objekat session je dostupan iz svih delova aplikacije. */
	
	/**
	 * Kreira DOM od XML dokumenta
	 */
	
	static {
    	File file = new File(".");
    	try {
			projectPath = file.getCanonicalPath().toString();
			System.out.println("projectPath " +  projectPath);
		} catch (IOException e) {
			System.out.println("Project path is not valid");
			e.printStackTrace();
		}
    	
    	session.put("korisnik",new JSONObject("{}"));
	}
	
	private static Document loadDocument(String file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(file));

			return document;
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void login() throws FileNotFoundException, IOException {
		
		System.out.println("login");
		boolean provera_username=false;
		boolean provera_password=false;
		
		try {

	//		File fXmlFile = new File("/XML2016/xml/users.xml");
			//File fXmlFile = new File(Application.projectPath+"/XML2016/xml/users.xml");
			//DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			//DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//Document doc = dBuilder.parse(fXmlFile);
			Document doc = XMLReader.run(Util.loadProperties(), "/users.xml");
			
			JSONObject obj = new JSONObject(params.get("body"));
			System.out.println("JSON"+obj.toString());
			if(obj.has("map"))
				obj.remove("map");
			String result = obj.toString();
			System.out.println(result);
	    	ObjectMapper mapper = new ObjectMapper();
	    	User user;
					user = mapper.readValue(result, User.class);
						
					String username = user.getUsername();
			 		String password = user.getPassword();
			 		
			 		System.out.println(username);
			 		System.out.println(password);
					
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("Korisnik");
					
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
						
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					if(username.equals(eElement.getElementsByTagName("KorisnickoIme").item(0).getTextContent()) ){
						 provera_username=true;
						
						if(password.equals(eElement.getElementsByTagName("Lozinka").item(0).getTextContent() )){
						 
						 System.out.println("Uspesan LOGIN");
						 provera_password=true;
						 User loggedUser = new User(username, password, password,
								 eElement.getElementsByTagName("Ime").item(0).getTextContent(), eElement.getElementsByTagName("Prezime").item(0).getTextContent()  , eElement.getElementsByTagName("Uloga").item(0).getTextContent()
								 , eElement.getElementsByTagName("Email").item(0).getTextContent());
						
						 
						 JSONObject user2 = new JSONObject(loggedUser);
						 session.put("korisnik", user2);
						 break;

						}
					}


				}
			}
			
			if(provera_username==false){
				
				renderJSON(new JSONObject("{'error':'Pogresan username.'}"));
	
			}
			
			if(provera_password==false){
				
				renderJSON(new JSONObject("{'error':'Pogresan password.'}"));
				//login();
			}
			
			if(provera_username && provera_password){
				renderJSON(new JSONObject("{'error':''}"));
			}
			
			
			
			
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		 }

    public static void index() {
    	System.out.println("Server je uspesno pokrenut");
    	render();
    }
    
    public static void index1() {
    	renderTemplate("views/Application/index1.html");
    }
    
    public static void save() {
    	System.out.println("save");
    	String result = params.get("body");
    	ObjectMapper mapper = new ObjectMapper();
    	Player player;
		try {
			player = mapper.readValue(result, Player.class);
			players.add(player);
			
			XMLPartialUpdate update = new XMLPartialUpdate();
			String patch = "\t<player>\n\t\t<name>"+player.getName()+"</name>\n"
					+ "\t\t<surname>"+player.getSurname()+"</surname>\n"
					+"\t\t<club>"+player.getClub()+"</club>\n"
					+"\t</player>";
			update.run(Util.loadProperties(), patch);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void saveUsers() {
    	System.out.println("save");
    	String result = params.get("body");
    	ObjectMapper mapper = new ObjectMapper();
    	User user;
    	boolean provera_password=false;
    	
		try {
			user = mapper.readValue(result, User.class);
			users.add(user);

	     	String username = user.getUsername();
	 		String password = user.getPassword();
	 		String repeat_password = user.getRepeat_password();
	 		String ime = user.getIme();
	 		String prezime = user.getPrezime();
	 		String uloga = user.getUloga();
	 		String email = user.getEmail();
	 //		String certificate = user.getCertificate();
	 		
	 		if(password.equalsIgnoreCase(repeat_password)){
	 			provera_password=true;
				 Date date = new Date();   
				    	String docUri = "/users.xml";
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
						namespaces.put("b", "http://www.ftn.uns.ac.rs/korisnici");
						namespaces.put("fn", "http://www.w3.org/2005/xpath-functions");

						// Assigning namespaces to patch builder
						DocumentPatchBuilder patchBuilder = xmlManager.newPatchBuilder();
						patchBuilder.setNamespaces(namespaces);

						String patch = 
						"<b:Korisnik>\n"+
						"\t<b:KorisnickoIme>"+username+"</b:KorisnickoIme>\n"+
						"\t<b:Lozinka>"+password+"</b:Lozinka>\n"+
						"\t<b:Ime>"+ime+"</b:Ime>\n"+
						"\t<b:Prezime>"+prezime+"</b:Prezime>\n"+
						"\t<b:Uloga>"+uloga+"</b:Uloga>\n"+
						"\t<b:Email>"+email+"</b:Email>\n"+
						"\t<b:TimeStamp>"+date+"</b:TimeStamp>\n"+
						"\t</b:Korisnik>\n";

						// Defining XPath context
						String contextXPath1 = "/b:Korisnici";

						patchBuilder.insertFragment(contextXPath1, Position.LAST_CHILD, patch);
						DocumentPatchHandle patchHandle = patchBuilder.build();

						System.out.println("[INFO] Inserting nodes to \"" + docId + "\".");
						xmlManager.patch(docId, patchHandle);

						// Release the client
						client.release();
						
				    	/*
				    if(new File(Application.projectPath+"/XML2016/data/"+certificate+".jks").exists()){

				    	boolean povucen = false;
				    	File f = new File(Application.projectPath+"/XML2016/data/"+"sgns-revoked.jks");
							if(f.exists() && !f.isDirectory()) {
								 KeyStoreReader ksr = new KeyStoreReader();
								    ksr.setKeyStoreFile(Application.projectPath+"/XML2016/data/"+"sgns-revoked.jks");
								    ksr.setPassword("sgns-revoked".toCharArray());
								    ksr.setKeyPass("test10".toCharArray());
								    HashMap<String,Certificate> sertifikati = ksr.readKeyStore();
								    Iterator it =  sertifikati.keySet().iterator();
								    while(it.hasNext())
								    {
								    	String sert = it.next().toString();
								    	
								    	if(sert.equals(certificate)){
								    		System.out.println("SERTIFIKAT JE POVUCEN!!!");
								    		povucen = true;
								    		break;
								    	}
								    }
							}
							
							
							if(!povucen){
							    SignEnveloped sign = new SignEnveloped();
							    sign.setIN_FILE(Application.projectPath+"/XML2016/xml/users.xml");
							    sign.setOUT_FILE(Application.projectPath+"/XML2016/xml/users.xml");
							    sign.setKEY_STORE_FILE(Application.projectPath+"/XML2016/data/"+certificate+".jks");
							    sign.setName(certificate);
							    sign.setPass(certificate);
							    sign.testIt();
							}
								
					
					    	EncryptKEK enc = new EncryptKEK();
						    enc.setIN_FILE(Application.projectPath+"/XML2016/xml/users.xml");
						    enc.setOUT_FILE(Application.projectPath+"/XML2016/xml/users.xml");
						    //   enc.setKEY_STORE_FILE(FilePaths.keystores+certificate+".jks");
						    enc.testIt();
				   
				   
				  
				    }
				    /*
				    
				   /* Marshalling marsh1 = new Marshalling();
			    	try {
						marsh1.test(kor);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		 		
			    	 //XMLWriter.run(Util.loadProperties());
	 		}
	 		
	 		if(provera_password==false){
	 			saveUsers();
	 	//		renderJSON(new JSONObject("{'error':'Sifre se moraju poklapati.'}"));
	 		}
	 		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void getPlayers() {
    	System.out.println("getPlayers");
    	renderJSON(players);
    }
    
    public static void getUser() {

    	renderJSON(session.get("korisnik"));
    }
    
    public static void logout(){
    	session.put("korisnik",new JSONObject("{}"));
    
    }
    
}