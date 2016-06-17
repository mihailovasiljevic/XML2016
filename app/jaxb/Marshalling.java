package jaxb;
import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import controllers.Application;
import models.User;


 
/** 
 * Primer 2.
 * 
 * Primer demonstrira "unmarshalling" tj. konverziju iz XML fajla
 * u objektni model, izmenu objektnog modela i "marshalling" na�?injenih
 * izmena, tj. njegovu serijalizaciju nazad u XML fajl.
 * 
 */
public class Marshalling {
	
	public void test(Korisnik user) throws Exception {
		try {
			System.out.println("[INFO] Example 2: JAXB unmarshalling/marshalling.\n");
			
			// Definiše se JAXB kontekst (putanja do paketa sa JAXB bean-ovima)
			JAXBContext context = JAXBContext.newInstance("jaxb");
			
			// Unmarshaller je objekat zadužen za konverziju iz XML-a u objektni model
			Unmarshaller unmarshaller = context.createUnmarshaller();

			Korisnici Korisnici = (Korisnici) unmarshaller.unmarshal(new File(Application.projectPath+"/XML2016/data/temp.xml"));
			
			// Izmena nad objektnim modelom dodavanjem novog odseka
		
			
			Korisnici.getKorisnik().add(user);
			
			// Marshaller je objekat zadužen za konverziju iz objektnog u XML model
			Marshaller marshaller = context.createMarshaller();
			
			// Podešavanje marshaller-a
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			// Umesto System.out-a, može se koristiti FileOutputStream
			marshaller.marshal(Korisnici, new File(Application.projectPath+"/XML2016/data/temp.xml"));
			marshaller.marshal(Korisnici, System.out);
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	private Korisnik createKorisnik(String string, String string2,String string3,String string4,String string5,String string6) {
		ObjectFactory factory = new ObjectFactory();
		Korisnik kor = factory.createKorisnik();
		kor.setKorisnickoIme(string);
		kor.setLozinka(string2);
		kor.setIme(string3);
		kor.setPrezime(string4);
		kor.setUloga(string5);
		kor.setEmail(string6);
		
		return kor;
	}
	
    public static void main( String[] args ) throws Exception {
    	Marshalling test = new Marshalling();
    	//test.test();
    }
}
