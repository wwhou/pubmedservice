package org.utils.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "firstName", "middleName", "LastName"
// "usedPakage", do I need to add this now? or defined in other class later?
})
/*
 * Author record the author information including first name, last name and
 * middle name. here I add affiliation but may be it's not needed
 */
@XmlRootElement(name = "Person")
public class Person {
	@XmlAttribute(name = "type", required = true)
	protected String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlElement(name = "ID", required = true)
	protected String id;
	@XmlElement(name = "firstName", required = true)
	protected String firstName;
	@XmlElement(name = "middleName", required = true)
	protected String middleName;
	@XmlElement(name = "lastName", required = true)
	protected String lastName;
	@XmlElement(name = "affiliation", required = false)
	protected List<Affiliation> affiliation;

	public List<Affiliation> getAffiliation() {
		if (affiliation == null) {
			affiliation = new ArrayList<Affiliation>();
		}
		return this.affiliation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
