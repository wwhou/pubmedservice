//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.30 at 04:57:27 PM EST 
//


package org.pubMed.jaxb.eSearch;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://pubMed/eSearch}TermSet"/>
 *         &lt;element ref="{http://pubMed/eSearch}OP"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "termSetOrOP"
})
@XmlRootElement(name = "TranslationStack")
public class TranslationStack {

    @XmlElements({
        @XmlElement(name = "TermSet", type = TermSet.class),
        @XmlElement(name = "OP", type = OP.class)
    })
    protected List<Object> termSetOrOP;

    /**
     * Gets the value of the termSetOrOP property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the termSetOrOP property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTermSetOrOP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TermSet }
     * {@link OP }
     * 
     * 
     */
    public List<Object> getTermSetOrOP() {
        if (termSetOrOP == null) {
            termSetOrOP = new ArrayList<Object>();
        }
        return this.termSetOrOP;
    }

}
