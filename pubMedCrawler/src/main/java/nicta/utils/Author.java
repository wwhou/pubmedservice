package nicta.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "firstName", "middleName", "LastName"
// "usedPakage", do I need to add this now? or defined in other class later?
})
/*
 * Author record the author information including first name, last name and
 * middle name. here I add affiliation but may be it's not needed 
 */
@XmlRootElement(name = "Author")
public class Author {
	@XmlElement(name = "firstName", required = true)
	protected String firstName;
	@XmlElement(name = "middleName", required = true)
	protected String middleName;
	@XmlElement(name = "lastName", required = true)
	protected String lastName;
	@XmlElement(name="affiliation", required=false)
	protected String affiliation;
	
	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
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
