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

import database.FilePaths;
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
	private static  String IN_FILE = FilePaths.korisnici;
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
		boolean provera=false;
		
		try {

	//		File fXmlFile = new File("/XML2016/xml/users.xml");
			File fXmlFile = new File(Application.projectPath+"/XML2016/xml/users.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			
			String result = params.get("body");
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
					
					if(username.equals(eElement.getElementsByTagName("KorisnickoIme").item(0).getTextContent()) && password.equals(eElement.getElementsByTagName("Lozinka").item(0).getTextContent() )){
						 
						 System.out.println("Uspesan LOGIN");
						 provera=true;
						 session.put("korisnik", user);
						 
					 }
					 		
					 		
					 		


					System.out.println("Staff id : " + eElement.getAttribute("id"));
					System.out.println("KorisnickoIme : " + eElement.getElementsByTagName("KorisnickoIme").item(0).getTextContent());
					System.out.println("Lozinka : " + eElement.getElementsByTagName("Lozinka").item(0).getTextContent());
					System.out.println("Ime : " + eElement.getElementsByTagName("Ime").item(0).getTextContent());
					System.out.println("Prezime : " + eElement.getElementsByTagName("Prezime").item(0).getTextContent());
					System.out.println("Uloga : " + eElement.getElementsByTagName("Uloga").item(0).getTextContent());
					System.out.println("Email : " + eElement.getElementsByTagName("Email").item(0).getTextContent());

				}
			}
			
			if(provera==false)
			login();
			
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
    	boolean provera=false;
    	
		try {
			user = mapper.readValue(result, User.class);
			users.add(user);

	     	String username = user.getUsername();
	 		String password = user.getPassword();
	 		String repeat_password = user.getRepeat_password();
	 		String ime = user.getIme();
	 		String prezime = user.getPrezime();
	 		String email = user.getEmail();
	 //		String certificate = user.getCertificate();
	 		
	 		if(password.equalsIgnoreCase(repeat_password)){
	 			
	 			provera=true;
	 			
		 		 Korisnik kor = new Korisnik();
				    kor.setKorisnickoIme(username);
				    kor.setLozinka(password);
				    kor.setIme(ime);
				    kor.setPrezime(prezime);
				    kor.setUloga("gradjanin");
				    kor.setEmail(email);
				    
				    /*Integer rbr = FileWriterReader.read("rbr.txt");
				    
				    kor.setRbrPoruke(rbr);
				    rbr++;
				    FileWriterReader.write("rbr.txt", rbr);*/
				    
				    Date date = new Date();
				    kor.setTimeStamp(date.toString());
				    	Marshalling marsh = new Marshalling();
				    	try {
							marsh.test(kor);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	
		
				    	
				    if(new File(FilePaths.keystores+certificate+".jks").exists()){

				    	boolean povucen = false;
				    	File f = new File(FilePaths.keystores+"sgns-revoked.jks");
							if(f.exists() && !f.isDirectory()) {
								 KeyStoreReader ksr = new KeyStoreReader();
								    ksr.setKeyStoreFile(FilePaths.keystores+"sgns-revoked.jks");
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
							    sign.setIN_FILE(FilePaths.korisnici);
							    sign.setOUT_FILE(FilePaths.korisnici);
							    sign.setKEY_STORE_FILE(FilePaths.keystores+certificate+".jks");
							    sign.setName(certificate);
							    sign.setPass(certificate);
							    sign.testIt();
							}
								
					
					    	EncryptKEK enc = new EncryptKEK();
						    enc.setIN_FILE(FilePaths.korisnici);
						    enc.setOUT_FILE(FilePaths.korisnici);
						    //   enc.setKEY_STORE_FILE(FilePaths.keystores+certificate+".jks");
						    enc.testIt();
				   
				   
				  
				    }
				    
				   /* Marshalling marsh1 = new Marshalling();
			    	try {
						marsh1.test(kor);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
		 		
			    	 XMLWriter.run(Util.loadProperties());
	 		}
	 		
	 		if(provera==false)
	 			saveUsers();
	 		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void getPlayers() {
    	System.out.println("getPlayers");
    	renderJSON(players);
    }
    
    public static void getUsers() {

    	renderJSON(users);
    }
    
    


}