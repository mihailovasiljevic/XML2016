package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.bouncycastle.jce.provider.JCEBlockCipher.RC2;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.DocumentPatchBuilder;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.io.marker.DocumentPatchHandle;
import com.marklogic.client.util.EditableNamespaceContext;

import database.Util;
import database.XMLPartialUpdate;
import play.mvc.Controller;
import rs.ac.uns.ftn.amandman.Amandman;
import rs.ac.uns.ftn.pravniakt.Deo;
import rs.ac.uns.ftn.pravniakt.Propis;
import xquery.XMLReader;

public class ActProcessing extends Controller {

	private static DatabaseClient client;
	
	private static DocumentBuilderFactory documentFactory;
	private static TransformerFactory transformerFactory;
	private static XPathFactory xPathFactory;
	private static Document documentAct;
	private static Document documentAma;
	
	static {

		/* Inicijalizacija DOM fabrike */
		documentFactory = DocumentBuilderFactory.newInstance();
		documentFactory.setNamespaceAware(true);
		documentFactory.setIgnoringComments(true);
		documentFactory.setIgnoringElementContentWhitespace(true);
		
		/* Inicijalizacija Transformer fabrike */
		transformerFactory = TransformerFactory.newInstance();
		
		/* Inicijalizacija XPath fabrike */
		xPathFactory = XPathFactory.newInstance();
		
	}
	
	public static void processAct(String uri) throws XPathExpressionException, FileNotFoundException, IOException, TransformerFactoryConfigurationError, TransformerException {
		
		
		String pathAma = "/amendments/"+uri+".xml";
		documentAma = XMLReader.run(Util.loadProperties(), pathAma);
		
		//String expression = "//*[@id=2]";
		//String expression = "/Propis/status";

		//ovde je potrebno pronaci oznaku akta
		
		String exp = "//*:oAkta";
		XPath xp = xPathFactory.newXPath();
		XPathExpression xpe;
		
		xp = xPathFactory.newXPath();
		
		xpe = xp.compile(exp);
		Node nodee = (Node) xpe.evaluate(documentAma, XPathConstants.NODE);
		String idd = nodee.getFirstChild().getNodeValue();
		
		String pathAct = "/acts/" + idd + ".xml";
		//--------------------------------------------------------
		
		documentAct = XMLReader.run(Util.loadProperties(), pathAct);
		
		
		String expressionId = "//*:id";
		
		XPath xPath = xPathFactory.newXPath();
		XPathExpression xPathExpression;
		
		xPath = xPathFactory.newXPath();

	
		xPathExpression = xPath.compile(expressionId);
		Node node = (Node) xPathExpression.evaluate(documentAma, XPathConstants.NODE);
		String id = node.getFirstChild().getNodeValue(); //nalazi se id element od amandmana
		
		//-----------------------------------
		expressionId = "//*[@id=" + id + "]";
		System.out.println("id exp " + expressionId);

		xPathExpression = xPath.compile(expressionId);
		node = (Node) xPathExpression.evaluate(documentAma, XPathConstants.NODE);
		
		node.setPrefix("akt");
		for(int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node nod = node.getChildNodes().item(i);
			if(nod.getPrefix() != null) {
				node.getChildNodes().item(i).setPrefix("akt");
			}
			for(int j = 0; j < node.getChildNodes().item(i).getChildNodes().getLength(); j++) {
				nod = node.getChildNodes().item(i).getChildNodes().item(j);
				if(nod.getPrefix() != null) {
					node.getChildNodes().item(i).getChildNodes().item(j).setPrefix("akt");
				}
			}
		}

		
		Transformer t = TransformerFactory.newInstance().newTransformer();
	    t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    StringWriter sw = new StringWriter();
	    t.transform(new DOMSource(node), new StreamResult(sw));
	    String forChangeAma = sw.toString();
	    
	    System.out.println("changing " + forChangeAma);
			
		xPathExpression = xPath.compile(expressionId);
		node = (Node) xPathExpression.evaluate(documentAct, XPathConstants.NODE);
		
		t = TransformerFactory.newInstance().newTransformer();
	    t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	    sw = new StringWriter();
	    t.transform(new DOMSource(node), new StreamResult(sw));
	    String forChangeAct = sw.toString();
		
	    
	    forChangeAma = forChangeAma.replace(" xmlns:akt=\"http://www.ftn.uns.ac.rs/amandman\" xmlns:ama=\"http://www.ftn.uns.ac.rs/amandman\" xmlns:pred=\"http://ftn.uns.ac.rs/pravniAkt/predicate/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:preda=\"http://ftn.uns.ac.rs/amandman/predicate\"", "");
	    System.out.println("amandment " + forChangeAma);
	    
	    System.out.println("id change: " + expressionId);
	    
	    XMLPartialUpdate.modifyAct(Util.loadProperties(), expressionId, forChangeAma, pathAct);
	    
	    
	    
	}

	
	/*public static void transform(Node node) {
		try {

			// Kreiranje instance objekta zaduzenog za serijalizaciju DOM modela
			Transformer transformer = transformerFactory.newTransformer();

			// Indentacija serijalizovanog izlaza
			transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			//transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			
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
	}*/
	
}
