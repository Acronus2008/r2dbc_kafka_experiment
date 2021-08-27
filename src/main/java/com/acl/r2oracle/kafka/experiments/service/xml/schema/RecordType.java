//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.25 at 09:49:32 AM CDT 
//


package com.acl.r2oracle.kafka.experiments.service.xml.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Calendar;


/**
 * <p>Java class for recordType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="allocation" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="allocation-timestamp"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}dateTime"&gt;
 *               &lt;enumeration value="2021-08-16T05:41:15.00Z"/&gt;
 *               &lt;enumeration value="2021-08-16T05:41:16.00Z"/&gt;
 *               &lt;enumeration value="2021-08-16T05:41:17.00Z"/&gt;
 *               &lt;enumeration value="2021-08-16T05:41:18.00Z"/&gt;
 *               &lt;enumeration value="2021-08-16T05:41:19.00Z"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="perpetual" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="preorder-backorder-handling" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="product-id" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordType", propOrder = {
    "allocation",
    "allocationTimestamp",
    "perpetual",
    "preorderBackorderHandling"
})
public class RecordType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    protected byte allocation;
    @XmlElement(name = "allocation-timestamp", required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected Calendar allocationTimestamp;
    @XmlElement(required = true)
    protected String perpetual;
    @XmlElement(name = "preorder-backorder-handling", required = true)
    protected String preorderBackorderHandling;
    @XmlAttribute(name = "product-id")
    protected Integer productId;

    /**
     * Gets the value of the allocation property.
     * 
     */
    public byte getAllocation() {
        return allocation;
    }

    /**
     * Sets the value of the allocation property.
     * 
     */
    public void setAllocation(byte value) {
        this.allocation = value;
    }

    /**
     * Gets the value of the allocationTimestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getAllocationTimestamp() {
        return allocationTimestamp;
    }

    /**
     * Sets the value of the allocationTimestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllocationTimestamp(Calendar value) {
        this.allocationTimestamp = value;
    }

    /**
     * Gets the value of the perpetual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPerpetual() {
        return perpetual;
    }

    /**
     * Sets the value of the perpetual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerpetual(String value) {
        this.perpetual = value;
    }

    /**
     * Gets the value of the preorderBackorderHandling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreorderBackorderHandling() {
        return preorderBackorderHandling;
    }

    /**
     * Sets the value of the preorderBackorderHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreorderBackorderHandling(String value) {
        this.preorderBackorderHandling = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProductId(Integer value) {
        this.productId = value;
    }

}
