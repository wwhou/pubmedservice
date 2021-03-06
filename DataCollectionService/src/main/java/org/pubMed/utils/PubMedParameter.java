package org.pubMed.utils;

//
//import javax.xml.bind.annotation.XmlAccessType;
//import javax.xml.bind.annotation.XmlAccessorType;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;

import org.utils.Parameter;
//
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "",propOrder = { "db", "term", "field", "retMax", "query_key",
//"webEnv", "idList", "translationSet", "translationStack",
//"queryTranslation", "error", "errorList", "warningList" })
//@XmlRootElement(name = "PubMedParameter")
public class PubMedParameter extends Parameter{

	public PubMedParameter(){
		super();
	}
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}


	public int getRetStart() {
		return retStart;
	}

	public void setRetStart(int retStart) {
		this.retStart = retStart;
	}

	public int getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(int queryKey) {
		this.queryKey = queryKey;
	}

	public String getWebEnv() {
		return webEnv;
	}

	public void setWebEnv(String webEnv) {
		this.webEnv = webEnv;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public int getReIdate() {
		return reIdate;
	}

	public void setReIdate(int reIdate) {
		this.reIdate = reIdate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isUseHistory() {
		return useHistory;
	}

	public void setUseHistory(boolean useHistory) {
		this.useHistory = useHistory;
	}

//	@XmlElement(name = "db", required = false)
	protected String database = "pubmed";
//	@XmlElement(name = "term", required = true)
	//protected String term = "";
//	@XmlElement(name = "field", required = false)
	protected String field = "";
//	@XmlElement(name = "retMax", required = false)
//	protected int retMax = 20;
//	@XmlElement(name = "retStart", required = false)
	protected int retStart = 0;
//	@XmlElement(name = "query_key", required = false)
	protected int queryKey = 0;
//	@XmlElement(name = "webEnv", required = false)
	protected String webEnv="";
//	@XmlElement(name = "datetype", required = false)
	protected String dateType = "";
//	@XmlElement(name = "reIdate", required = false)
	protected int reIdate = 0;
//	@XmlElement(name = "mindate", required = false)
	protected String startDate = "";
//	@XmlElement(name = "maxdate", required = false)
	protected String endDate = "";
//	@XmlElement(name = "usehistory", required = false)
	protected boolean useHistory=true;

}
