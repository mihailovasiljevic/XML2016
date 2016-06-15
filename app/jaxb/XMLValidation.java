package jaxb;
import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import controllers.Application;
import rs.ac.uns.ftn.amandman.Amandman;
import rs.ac.uns.ftn.pravniakt.Propis;

/** 
 * Primer 3.
 * 
 * Primer demonstrira XML schema validaciju prilikom 
 * unmarshalovanja objekta iz XML fajla.
 *  
 */
public class XMLValidation {
	
	public boolean test(String schemaPath,String type) {
		boolean isValid = true;
		try {
			System.out.println("[INFO] Example 3: JAXB XML Schema validation .\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			
			JAXBContext context = null;
			if(type.equals("act"))
			context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");
			else if(type.equals("amandman"))
				context = JAXBContext.newInstance("rs.ac.uns.ftn.amandman");
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// XML schema validacija "./data/akt.xsd"
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(schemaPath));
            
			// Podešavanje unmarshaller-a za XML schema validaciju
			unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
			
            // Test: proširiti XML fajl nepostojećim elementom (npr. <test></test>)
            Propis propis= new Propis();
            Amandman amandman = new Amandman();
            if(type.equals("act"))
            propis = (Propis) unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
            else if(type.equals("amandman"))
            	amandman = (Amandman)unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
            // Ispis sadržaja objektnog modela, nakon uspešne validacije
            //printStudent(student);
			System.out.println(schemaPath);
			System.out.println(context);
		} catch (JAXBException e) {
			e.printStackTrace();
			isValid = false;
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return isValid;
	}
	
	/*
	private void printStudent(Student student) {
		
		// Prikaz detalja o položenim ispitima
		System.out.println("- Položeni ispiti \n");
		for(PolozenIspit polozenIspit : student.getPolozeniIspiti())
			printPolozenIspit(polozenIspit);
		
		// Prikaz detalja o odslušanim predmetima
		System.out.println("- Odslušani predmeti \n");
		for(Predmet predmet : student.getOdslusaniPredmeti().getList())
			printOdslusaniPredmet(predmet);
			
	}
	
	
	private void printPolozenIspit(PolozenIspit polozenIspit) {
		
		System.out.print("\t- Ispit: " + polozenIspit.getPredmet().getNaziv() + ", ");
		System.out.print(polozenIspit.getPredmet().getNastavnik() + ", ");
		System.out.print(polozenIspit.getOcena() + ", ");
		System.out.print(MyDatatypeConverter.printDate(polozenIspit.getDatum()) + ", ");
		System.out.println("šk. godina: " + polozenIspit.getPredmet().getSkolskaGodina() + ".");
		System.out.println();
		
	}
	
	private void printOdslusaniPredmet(Predmet predmet) {
		
		System.out.print("\t- Predmet: " + predmet.getNaziv() + ", ");
		System.out.print(predmet.getNastavnik() + ", ");
		System.out.println("šk. godina: " + predmet.getSkolskaGodina() + ". ");
		System.out.println();
		
	}
	
    public static void main( String[] args ) {
    	Example3Validation test = new Example3Validation();
    	test.test();
    }
    */
}
