//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.16 at 11:12:24 AM CEST 
//


package rs.ac.uns.ftn.pravniakt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rs.ac.uns.ftn.pravniakt package. 
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

    private final static QName _ClanStavTekstReferenca_QNAME = new QName("http://www.ftn.uns.ac.rs/pravniAkt", "referenca");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rs.ac.uns.ftn.pravniakt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Clan }
     * 
     */
    public Clan createClan() {
        return new Clan();
    }

    /**
     * Create an instance of {@link Clan.Stav }
     * 
     */
    public Clan.Stav createClanStav() {
        return new Clan.Stav();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tacka }
     * 
     */
    public Clan.Stav.Tacka createClanStavTacka() {
        return new Clan.Stav.Tacka();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tacka.Podtacka }
     * 
     */
    public Clan.Stav.Tacka.Podtacka createClanStavTackaPodtacka() {
        return new Clan.Stav.Tacka.Podtacka();
    }

    /**
     * Create an instance of {@link Propis }
     * 
     */
    public Propis createPropis() {
        return new Propis();
    }

    /**
     * Create an instance of {@link Deo }
     * 
     */
    public Deo createDeo() {
        return new Deo();
    }

    /**
     * Create an instance of {@link Odeljak }
     * 
     */
    public Odeljak createOdeljak() {
        return new Odeljak();
    }

    /**
     * Create an instance of {@link Referenca }
     * 
     */
    public Referenca createReferenca() {
        return new Referenca();
    }

    /**
     * Create an instance of {@link Pododeljak }
     * 
     */
    public Pododeljak createPododeljak() {
        return new Pododeljak();
    }

    /**
     * Create an instance of {@link Prilog }
     * 
     */
    public Prilog createPrilog() {
        return new Prilog();
    }

    /**
     * Create an instance of {@link Glava }
     * 
     */
    public Glava createGlava() {
        return new Glava();
    }

    /**
     * Create an instance of {@link Clan.Tekst }
     * 
     */
    public Clan.Tekst createClanTekst() {
        return new Clan.Tekst();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tekst }
     * 
     */
    public Clan.Stav.Tekst createClanStavTekst() {
        return new Clan.Stav.Tekst();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tacka.Tekst }
     * 
     */
    public Clan.Stav.Tacka.Tekst createClanStavTackaTekst() {
        return new Clan.Stav.Tacka.Tekst();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tacka.Podtacka.Tekst }
     * 
     */
    public Clan.Stav.Tacka.Podtacka.Tekst createClanStavTackaPodtackaTekst() {
        return new Clan.Stav.Tacka.Podtacka.Tekst();
    }

    /**
     * Create an instance of {@link Clan.Stav.Tacka.Podtacka.Alineja }
     * 
     */
    public Clan.Stav.Tacka.Podtacka.Alineja createClanStavTackaPodtackaAlineja() {
        return new Clan.Stav.Tacka.Podtacka.Alineja();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Referenca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/pravniAkt", name = "referenca", scope = Clan.Stav.Tekst.class)
    public JAXBElement<Referenca> createClanStavTekstReferenca(Referenca value) {
        return new JAXBElement<Referenca>(_ClanStavTekstReferenca_QNAME, Referenca.class, Clan.Stav.Tekst.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Referenca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/pravniAkt", name = "referenca", scope = Clan.Stav.Tacka.Tekst.class)
    public JAXBElement<Referenca> createClanStavTackaTekstReferenca(Referenca value) {
        return new JAXBElement<Referenca>(_ClanStavTekstReferenca_QNAME, Referenca.class, Clan.Stav.Tacka.Tekst.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Referenca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/pravniAkt", name = "referenca", scope = Clan.Tekst.class)
    public JAXBElement<Referenca> createClanTekstReferenca(Referenca value) {
        return new JAXBElement<Referenca>(_ClanStavTekstReferenca_QNAME, Referenca.class, Clan.Tekst.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Referenca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/pravniAkt", name = "referenca", scope = Clan.Stav.Tacka.Podtacka.Tekst.class)
    public JAXBElement<Referenca> createClanStavTackaPodtackaTekstReferenca(Referenca value) {
        return new JAXBElement<Referenca>(_ClanStavTekstReferenca_QNAME, Referenca.class, Clan.Stav.Tacka.Podtacka.Tekst.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Referenca }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.ftn.uns.ac.rs/pravniAkt", name = "referenca", scope = Clan.Stav.Tacka.Podtacka.Alineja.class)
    public JAXBElement<Referenca> createClanStavTackaPodtackaAlinejaReferenca(Referenca value) {
        return new JAXBElement<Referenca>(_ClanStavTekstReferenca_QNAME, Referenca.class, Clan.Stav.Tacka.Podtacka.Alineja.class, value);
    }

}
