package org.lens.crawler;
import java.util.List;
import java.util.Map;

public class Patent {

	/**
	 * @param args
	 */
	private String ID;
	private String title;
	private String claim;
	private Map<String, String>classification;
	private List<String> inventors;
	private List<String> applicants; 
	private String applicationNumber;
	private String publicationDate;
	private String abstracts;

	
	public String getAbstracts() {
		return abstracts;
	}


	public void setAbstracts(String abstracts) {
		this.abstracts = abstracts;
	}


	public String getID() {
		return ID;
	}


	public void setID(String iD) {
		ID = iD;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getClaim() {
		return claim;
	}


	public void setClaim(String claim) {
		this.claim = claim;
	}


	public Map<String, String> getClassification() {
		return classification;
	}


	public void setClassification(Map<String,String> classification) {
		this.classification = classification;
	}
	public List<String> getInventors() {
		return inventors;
	}


	public void setInventors(List<String> inventors) {
		this.inventors = inventors;
	}


	public List<String> getApplicants() {
		return applicants;
	}


	public void setApplicants(List<String> applicants) {
		this.applicants = applicants;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}


	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}


	public String getPublicationDate() {
		return publicationDate;
	}


	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
