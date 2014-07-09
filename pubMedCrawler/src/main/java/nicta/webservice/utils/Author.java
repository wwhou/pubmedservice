package nicta.webservice.utils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Author {

	@XmlElement(name = "firstName", required = true)
	protected String firstName;
	@XmlElement(name = "middleName", required = false)
	protected String middleName;
	@XmlElement(name = "lastName", required = true)
	protected String lastName;
	@XmlElement(name = "affiliations", required = false)
	protected String institution;

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
