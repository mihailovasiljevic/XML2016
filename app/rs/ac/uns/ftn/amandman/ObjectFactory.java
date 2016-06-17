//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.17 at 10:30:15 AM CEST 
//


package rs.ac.uns.ftn.amandman;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.amandman package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _StavTacka_QNAME = new QName("http://www.ftn.uns.ac.rs/amandman", "tacka");
    private final static QName _PodtackaAlineja_QNAME = new QName("http://www.ftn.uns.ac.rs/amandman", "alineja");
    private final static QName _TackaPodtacka_QNAME = new QName("http://www.ftn.uns.ac.rs/amandman", "podtacka");
    private final static QName _ClanStav_QNAME = new QName("http://www.ftn.uns.ac.rs/amandman", "stav");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.amandman
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Amandman }
     * 
     */
    public Amandman createAmandman() {
        return new Amandman();
    }

    /**
     * Create an instance of {@link Amandman.GlavniDeo }
     * 
     */
    public Amandman.GlavniDeo createAmandmanGlavniDeo() {
        return new Amandman.GlavniDeo();
    }

    /**
     * Create an instance of {@link Amandman.GlavniDeo.Clan }
     * 
     */
    public Amandman.GlavniDeo.Clan createAmandmanGlavniDeoClan() {
        return new Amandman.GlavniDeo.Clan();
    }

    /**
     * Create an instance of {@link Amandman.GlavniDeo.Clan.Stav }
     * 
     */
    public Amandman.GlavniDeo.Clan.Stav createAmandmanGlavniDeoClanStav() {
        return new Amandman.GlavniDeo.Clan.Stav();
    }

    /**
     * Create an instance of {@link Amandman.Uvod }
     * 
     */
    public Amandman.Uvod createAmandmanUvod() {
        return new Amandman.Uvod();
    }

    /**
     * Create an instance of {@link Amandman.Zaglavlje }
     * 
     */
    public Amandman.Zaglavlje createAmandmanZaglavlje() {
        return new Amandman.Zaglavlje();
    }

    /**
     * Create an instance of {@link Amandman.Obrazlozenje }
     * 
     */
    public Amandman.Obrazlozenje createAmandmanObrazlozenje() {
        return new Amandman.Obrazlozenje();
    }

    /**
     * Create an instance of {@link Amandman.Potpis }
     * 
     */
    public Amandman.Potpis createAmandmanPotpis() {
        return new Amandman.Potpis();
    }

    /**
     * Create an instance of {@link rs.ac.uns.ftn.amandman.Stav }
     * 
     */
    public rs.ac.uns.ftn.amandman.Stav createStav() {
        return new rs.ac.uns.ftn.amandman.Stav();
    }

    /**
     * Create an instance of {@link Odeljak }
     * 
     */
    public Odeljak createOdeljak() {
        return new Odeljak();
    }

    /**
     * Create an instance of {@link rs.ac.uns.ftn.amandman.Clan }
     * 
     */
    public rs.ac.uns.ftn.amandman.Clan createClan() {
        return new rs.ac.uns.ftn.amandman.Clan();
    }

    /**
     * Create an instance of {@link Glava }
     * 
     */
    public Glava createGlava() {
        return new Glava();
    }

    /**
     * Create an instance of {@link Akt }
     * 
     */
    public Akt createAkt() {
        return new Akt();
    }

    /**
     * Create an instance of {@link Podtacka }
     * 
     */
    public Podtacka createPodtacka() {
        return new Podtacka();
    }

    /**
     * Create an instance of {@link rs.ac.uns.ftn.amandman.Tacka }
     * 
     */
    public rs.ac.uns.ftn.amandman.Tacka createTacka() {
        return new rs.ac.uns.ftn.amandman.Tacka();
    }

    /**
     * Create an instance of {@link Pododeljak }
     * 
     */
    public Pododeljak createPododeljak() {
        return new Pododeljak();
    }

    /**
     * Create an instance of {@link Deo }
     * 
     */
    public Deo createDeo() {
        return new Deo();
    }

    /**
     * Create an instance of {@link Amandman.GlavniDeo.Clan.Stav.Tacka }
     * 
     */
    public Amandman.GlavniDeo.Clan.Stav.Tacka createAmandmanGlavniDeoClanStavTacka() {
        return new Amandman.GlavniDeo.Clan.Stav.Tacka();
    }

    /**
     * Create an instance of {@link Amandman.Uvod.Propis }
     * 
     */
    public Amandman.Uvod.Propis createAmandmanUvodPropis() {
        return new Amandman.Uvod.Propis();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link rs.ac.uns.ftn.amandman.Tacka }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/amandman", name = "tacka", scope = rs.ac.uns.ftn.amandman.Stav.class)
    public JAXBElement<rs.ac.uns.ftn.amandman.Tacka> createStavTacka(rs.ac.uns.ftn.amandman.Tacka value) {
        return new JAXBElement<rs.ac.uns.ftn.amandman.Tacka>(_StavTacka_QNAME, rs.ac.uns.ftn.amandman.Tacka.class, rs.ac.uns.ftn.amandman.Stav.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/amandman", name = "alineja", scope = Podtacka.class)
    public JAXBElement<String> createPodtackaAlineja(String value) {
        return new JAXBElement<String>(_PodtackaAlineja_QNAME, String.class, Podtacka.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Podtacka }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/amandman", name = "podtacka", scope = rs.ac.uns.ftn.amandman.Tacka.class)
    public JAXBElement<Podtacka> createTackaPodtacka(Podtacka value) {
        return new JAXBElement<Podtacka>(_TackaPodtacka_QNAME, Podtacka.class, rs.ac.uns.ftn.amandman.Tacka.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link rs.ac.uns.ftn.amandman.Stav }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/amandman", name = "stav", scope = rs.ac.uns.ftn.amandman.Clan.class)
    public JAXBElement<rs.ac.uns.ftn.amandman.Stav> createClanStav(rs.ac.uns.ftn.amandman.Stav value) {
        return new JAXBElement<rs.ac.uns.ftn.amandman.Stav>(_ClanStav_QNAME, rs.ac.uns.ftn.amandman.Stav.class, rs.ac.uns.ftn.amandman.Clan.class, value);
    }

}
