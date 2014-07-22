package nicta.utils;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "keyword"
})
@XmlRootElement(name = "KeywordList")
public class KeywordList {
	 @XmlElement(name = "Keyword")
	    protected List<Keyword> keyword;
	    public List<Keyword> getId() {
	        if (keyword == null) {
	           keyword = new ArrayList<Keyword>();
	        }
	        return this.keyword;
	    }
}
