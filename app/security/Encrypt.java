package security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import controllers.Application;


//Generise tajni kljuc
//Kriptije sadrzaj elementa student tajnim kljucem
//Snima tajni kljuc u fajl
public class Encrypt {
	
	private  String IN_FILE = "./xml/akt.xml";
	private  String OUT_FILE = "./xml/akt_enc.xml";
	private  static final String KEY_FILE = Application.projectPath+"/XML2016/data/"+"dataKey1.key";
	
    static {
    	//staticka inicijalizacija
        Security.addProvider(new BouncyCastleProvider());
        org.apache.xml.security.Init.init();
    }
	
	public void testIt() {
		//ucitava se dokument
		Document doc = loadDocument(IN_FILE);
		//generise tajni kljuc
		System.out.println("Generating secret key ....");
		SecretKey secretKey = generateDataEncryptionKey();
		//kriptuje se dokument
		System.out.println("Encrypting....");
		doc = encrypt(doc ,secretKey);
		//snima se tajni kljuc
		storeDataEncryptionKey(secretKey, KEY_FILE);
		//snima se dokument
		saveDocument(doc, OUT_FILE);
		System.out.println("Encryption done");
	}
	
	/**
	 * Kreira DOM od XML dokumenta
	 */
	private Document loadDocument(String file) {
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
	
	/**
	 * Snima DOM u XML fajl 
	 */
	private void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			
			transformer.transform(source, result);

			f.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generise tajni kljuc
	 */
	private SecretKey generateDataEncryptionKey() {

        try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede"); //Triple DES
			return keyGenerator.generateKey();
		
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	/**
	 * Snima tajni kljuc
	 */
	private void storeDataEncryptionKey(SecretKey key, String fileName) {
		 try {
			byte[] keyBytes = key.getEncoded();
			 File kekFile = new File(fileName);
			 FileOutputStream f = new FileOutputStream(kekFile);
			 f.write(keyBytes);
			 f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kriptuje sadrzaj prvog elementa odsek
	 */
	private Document encrypt(Document doc, SecretKey key) {
		
		try {
			//cipher za kriptovanje XML-a
			XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
			//inicijalizacija za kriptovanje
			xmlCipher.init(XMLCipher.ENCRYPT_MODE, key);
			
			//trazi se element ciji sadrzaj se kriptuje
			NodeList odseci = doc.getElementsByTagName("Email");
			Element odsek = (Element) odseci.item(0);
			
			xmlCipher.doFinal(doc, odsek, true); //kriptuje sa sadrzaj
			
			return doc;
		} catch (XMLEncryptionException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		Encrypt encrypt = new Encrypt();
	//	encrypt.testIt();
	}
	
	public String getIN_FILE() {
		return IN_FILE;
	}

	public void setIN_FILE(String iN_FILE) {
		IN_FILE = iN_FILE;
	}

	public String getOUT_FILE() {
		return OUT_FILE;
	}

	public void setOUT_FILE(String oUT_FILE) {
		OUT_FILE = oUT_FILE;
	}

	
}
