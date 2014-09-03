package org.utils.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "patent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "juristiction", "opiDate", "priorities" })
public class Patent {

	@XmlElement(name = "juristiction", required = false)
	String juristiction;
	@XmlElement(name = "opiDate", required = false)
	String opiDate;
	@XmlElement(name = "priorities", required = false)
	List<Priority> priorities;

	public String getJuristiction() {
		return juristiction;
	}

	public void setJuristiction(String juristiction) {
		this.juristiction = juristiction;
	}

	public String getOpiDate() {
		return opiDate;
	}

	public void setOpiDate(String opiDate) {
		this.opiDate = opiDate;
	}

	public List<Priority> getPriority(){
		if(priorities==null){
			priorities=new ArrayList<Priority>();
		}
		return priorities;
	}

}
