package jaxb;
import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import rs.ac.uns.ftn.pravniakt.Propis;

/** 
 * Primer 3.
 * 
 * Primer demonstrira XML schema validaciju prilikom 
 * unmarshalovanja objekta iz XML fajla.
 *  
 */
public class XMLValidation {
	
	public boolean test(String schemaPath) {
		boolean isValid = true;
		try {
			System.out.println("[INFO] Example 3: JAXB XML Schema validation .\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			
			//DA LI BI OVO TREBALO DA JE PROMENLJIVO?
			JAXBContext context = JAXBContext.newInstance("rs.ac.uns.ftn.pravniakt");

			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();
			
			// XML schema validacija "./data/akt.xsd"
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(schemaPath));
            
			// Podešavanje unmarshaller-a za XML schema validaciju
			unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new MyValidationEventHandler());
			
            // Test: proširiti XML fajl nepostojećim elementom (npr. <test></test>)
            Propis propis = (Propis) unmarshaller.unmarshal(new File("./data/temp.xml"));

            // Ispis sadržaja objektnog modela, nakon uspešne validacije
            //printStudent(student);
			
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
