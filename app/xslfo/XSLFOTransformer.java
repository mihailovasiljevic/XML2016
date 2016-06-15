package xslfo;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.TransformerFactoryImpl;

import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import sun.io.CharacterEncoding;
import controllers.Application;

/**
 * 
 * Primer demonstrira koriscenje programskog API-a za 
 * renderovanje PDF-a na osnovu XSL-FO transformacije.
 *
 */
public class XSLFOTransformer {
	
	private FopFactory fopFactory;
	
	private TransformerFactory transformerFactory;
	
	public XSLFOTransformer() throws SAXException, IOException {
		
		File file = new File(Application.projectPath+"/XML2016/app/xslfo/fop.xconf");
		fopFactory = FopFactory.newInstance(file);
		
		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	public void transform(String text, String actId) throws Exception {

		System.out.println("[INFO] " + XSLFOTransformer.class.getSimpleName());
		
		// Point to the XSL-FO file
		File xsltFile = new File("XML2016/data/akt.xsl");

		// Create transformation source
		StreamSource transformSource = new StreamSource(xsltFile);
		
		// Initialize the transformation subject
		//StreamSource source = new StreamSource(new File("XML2016/data/akt.xml"));
		
		//InputStream stream = new ByteArrayInputStream(text.getBytes("UTF-8"));
		 
		//StreamSource source = new StreamSource(stream);
		File xmlFile = File.createTempFile("akttmp", ".html");
		FileUtils.writeStringToFile(xmlFile, text);
		StreamSource source = new StreamSource(xmlFile);
		
		//new StreamSource()
		// Initialize user agent needed for the transformation
		FOUserAgent userAgent = fopFactory.newFOUserAgent();
		
		// Create the output stream to store the results
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		// Initialize the XSL-FO transformer object
		Transformer xslFoTransformer = transformerFactory.newTransformer(transformSource);
		xslFoTransformer.setOutputProperty("encoding", "UTF-8");
		
		// Construct FOP instance with desired output format
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, outStream);
		
		System.out.println("starting");

		// Resulting SAX events 
		Result res = new SAXResult(fop.getDefaultHandler());

		// Start XSLT transformation and FOP processing
		
		xslFoTransformer.transform(source, res);
		
		// Generate PDF file
		File pdfFile = new File("XML2016/acts/act_"+actId+".pdf");
		OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
		out.write(outStream.toByteArray());
	
		System.out.println("[INFO] File \"" + pdfFile.getCanonicalPath() + "\" generated successfully.");
		out.close();
		
		System.out.println("[INFO] End.");

	}

}
