//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.17 at 11:48:34 AM CEST 
//


package rs.ac.uns.ftn.pravniakt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Glava complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Glava">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="redniBroj" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="simbolickiNaziv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naziv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;choice>
 *             &lt;element name="clan" type="{http://www.ftn.uns.ac.rs/pravniAkt}clan"/>
 *             &lt;element name="odeljak" type="{http://www.ftn.uns.ac.rs/pravniAkt}Odeljak"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="tipOznacavanja">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="bezTacke"/>
 *             &lt;enumeration value="saTackom"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Glava", propOrder = {
    "redniBroj",
    "simbolickiNaziv",
    "naziv",
    "clanOrOdeljak"
})
public class Glava {

    @XmlElement(required = true)
    protected BigInteger redniBroj;
    @XmlElement(required = true)
    protected String simbolickiNaziv;
    @XmlElement(required = true)
    protected String naziv;
    @XmlElements({
        @XmlElement(name = "clan", type = Clan.class),
        @XmlElement(name = "odeljak", type = Odeljak.class)
    })
    protected List<Object> clanOrOdeljak;
    @XmlAttribute(name = "tipOznacavanja")
    protected String tipOznacavanja;

    /**
     * Gets the value of the redniBroj property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRedniBroj() {
        return redniBroj;
    }

    /**
     * Sets the value of the redniBroj property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRedniBroj(BigInteger value) {
        this.redniBroj = value;
    }

    /**
     * Gets the value of the simbolickiNaziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimbolickiNaziv() {
        return simbolickiNaziv;
    }

    /**
     * Sets the value of the simbolickiNaziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimbolickiNaziv(String value) {
        this.simbolickiNaziv = value;
    }

    /**
     * Gets the value of the naziv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Sets the value of the naziv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaziv(String value) {
        this.naziv = value;
    }

    /**
     * Gets the value of the clanOrOdeljak property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clanOrOdeljak property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClanOrOdeljak().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Clan }
     * {@link Odeljak }
     * 
     * 
     */
    public List<Object> getClanOrOdeljak() {
        if (clanOrOdeljak == null) {
            clanOrOdeljak = new ArrayList<Object>();
        }
        return this.clanOrOdeljak;
    }

    /**
     * Gets the value of the tipOznacavanja property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipOznacavanja() {
        return tipOznacavanja;
    }

    /**
     * Sets the value of the tipOznacavanja property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipOznacavanja(String value) {
        this.tipOznacavanja = value;
    }

}
