package xslfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;

public class XSLTransformer {

	public static String transform(String xml) {
		
		String result;
		OutputStream stream;
		String back = null;
		System.out.println("ispred try");
		try {
			File tempFile = File.createTempFile("tempfile", ".html");
			File xmlFile = File.createTempFile("akttmp", ".html");
			
			FileUtils.writeStringToFile(xmlFile, xml);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Transformer transformer = tFactory.newTransformer(new StreamSource("XML2016/data/akt_xhtml.xsl"));
			transformer.transform(
				   new StreamSource(xmlFile),
			       new StreamResult(tempFile));
			back = FileUtils.readFileToString(tempFile);
			System.out.println(back);
	    }
		catch (Exception e) {
			e.printStackTrace( );
	    }
		return back;
	}
	
	public static String transformAma(String xml) {
		
		String result;
		OutputStream stream;
		String back = null;
		System.out.println("ispred try");
		try {
			File tempFile = File.createTempFile("tempfile", ".html");
			File xmlFile = File.createTempFile("akttmp", ".html");
			
			FileUtils.writeStringToFile(xmlFile, xml);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();

			Transformer transformer = tFactory.newTransformer(new StreamSource("XML2016/data/amandman_xhtml.xsl"));
			transformer.transform(
				   new StreamSource(xmlFile),
			       new StreamResult(tempFile));
			back = FileUtils.readFileToString(tempFile);
	    }
		catch (Exception e) {
			e.printStackTrace( );
	    }
		return back;
	}
	
}
