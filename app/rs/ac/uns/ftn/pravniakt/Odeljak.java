//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5.1 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.17 at 12:01:53 PM CEST 
//


package rs.ac.uns.ftn.pravniakt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Odeljak complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Odeljak">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="redniBroj" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="simbolickiNaziv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="naziv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;choice>
 *             &lt;element name="clan" type="{http://www.ftn.uns.ac.rs/pravniAkt}clan"/>
 *             &lt;element name="pododeljak" type="{http://www.ftn.uns.ac.rs/pravniAkt}Pododeljak"/>
 *           &lt;/choice>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Odeljak", propOrder = {
    "redniBroj",
    "simbolickiNaziv",
    "naziv",
    "clanOrPododeljak"
})
public class Odeljak {

    @XmlElement(required = true)
    protected BigInteger redniBroj;
    @XmlElement(required = true)
    protected String simbolickiNaziv;
    @XmlElement(required = true)
    protected String naziv;
    @XmlElements({
        @XmlElement(name = "clan", type = Clan.class),
        @XmlElement(name = "pododeljak", type = Pododeljak.class)
    })
    protected List<Object> clanOrPododeljak;

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
     * Gets the value of the clanOrPododeljak property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clanOrPododeljak property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClanOrPododeljak().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Clan }
     * {@link Pododeljak }
     * 
     * 
     */
    public List<Object> getClanOrPododeljak() {
        if (clanOrPododeljak == null) {
            clanOrPododeljak = new ArrayList<Object>();
        }
        return this.clanOrPododeljak;
    }

}
