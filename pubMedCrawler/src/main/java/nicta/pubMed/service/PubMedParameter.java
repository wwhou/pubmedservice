package nicta.pubMed.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = { "db", "term", "field", "retMax","query_key",
//		"webEnv", "idList", "translationSet", "translationStack",
//		"queryTranslation", "error", "errorList", "warningList" })
@XmlRootElement
public class PubMedParameter {

	@XmlElement(name = "db", required = false)
	protected String database = "pubmed";
	@XmlElement(name = "term", required = true)
	protected String term="";
	@XmlElement(name = "field", required = false)
	protected String field="";
	@XmlElement(name = "retMax", required = false)
	protected int retMax=20;
	@XmlElement(name = "retStart", required = false)
	protected int retStart=0;
	@XmlElement(name = "query_key", required = false)
	protected int queryKey=0;
	@XmlElement(name = "webEnv", required = false)
	protected String webEnv;
	@XmlElement(name = "datetype", required = false)
	protected String dateType="";
	@XmlElement(name = "reIdate", required = false)
	protected int reIdate=0;
	@XmlElement(name = "mindate", required = false)
	protected String startDate="";
	@XmlElement(name = "maxdate", required = false)
	protected String endDate="";
	@XmlElement(name = "usehistory", required = false)
	protected boolean useHistory;

}
