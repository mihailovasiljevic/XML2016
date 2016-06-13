package controllers;

import play.*;
import play.cache.Cache;
import play.mvc.*;
import rs.ac.uns.ftn.pravniakt.Propis;
import util.FileUtil;
import xquery.XMLReader;
import xquery.XMLWriter;
import org.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.FilePaths;
import database.XMLPartialUpdate;
import database.XMLWriterUriTemplate;
import database.Util;
import models.Player;
import models.User;

public class Application extends Controller {

	public static String projectPath;
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<User> users = new ArrayList<User>();
	private static  String IN_FILE = FilePaths.korisnici;
	
	/** Ukoliko je potrebno koristiti sesiju to je moguce uz pomoc objekta session na sledeci nacin:
	 * session.put(key, value). Sesija se preuzima sa session.get(value). 
	 * Objekat session je dostupan iz svih delova aplikacije. */
	
	/**
	 * Kreira DOM od XML dokumenta
	 */
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
		System.out.println(IN_FILE);
		 XMLReader.run(Util.loadProperties());
	
		
		Document doc = loadDocument(IN_FILE);
		NodeList odseci = doc.getElementsByTagName("Lozinka");
		Element odsek = (Element) odseci.item(0);
		
		System.out.println(doc);
		System.out.println(odsek.toString());
		
		//		Korisnici users=doc.getElementsByTagName("KorisnickoIme");
		
		System.out.println("save");
    	String result = params.get("body");
    	ObjectMapper mapper = new ObjectMapper();
    	User user;
    	
    	/*
			try {
				user = mapper.readValue(result, User.class);
				users.add(user);
				
				 System.out.println(user);
				
				String username = user.getUsername();
		 		String password = user.getPassword();
				
				 for(Korisnik us : users){
					 
					 System.out.println("for");
					 if(username.equals(us.getKorisnickoIme()) && password.equals(us.getLozinka()) ){
						 
						 System.out.println("Uspesan LOGIN");
						 
					 }
					 
				 }
				 
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}
	
    public static void index() {
    	System.out.println("Server je uspesno pokrenut");
    	File file = new File(".");
    	try {
			projectPath = file.getCanonicalPath().toString();
		} catch (IOException e) {
			System.out.println("Project path is not valid");
			e.printStackTrace();
		}
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
    	
		try {
			user = mapper.readValue(result, User.class);
			users.add(user);

	     	String username = user.getUsername();
	 		String password = user.getPassword();
	 		String repeat_password = user.getRepeat_password();
	 		String ime = user.getIme();
	 		String prezime = user.getPrezime();
	 		String email = user.getEmail();
	 		
	 		if(password.equalsIgnoreCase(repeat_password)){
		 		 Korisnik kor = new Korisnik();
				    kor.setKorisnickoIme(username);
				    kor.setLozinka(password);
				    kor.setIme(ime);
				    kor.setPrezime(prezime);
				    kor.setUloga("gradjanin");
				    kor.setEmail(email);
				    
				    Marshalling marsh = new Marshalling();
			    	try {
						marsh.test(kor);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 		
			    	 XMLWriter.run(Util.loadProperties());
	 		}
	 		
	 		
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
    		System.out.println("XML NIJE VALIDAN");
    	
    	
    	
    	if(xmlValid){
    		try {
				XMLWriterUriTemplate.run(Util.loadProperties());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    		
    	
    	}
	
		
    
    
    public static void getAct(){
    	System.out.println("GET ACT");
    
    }

}