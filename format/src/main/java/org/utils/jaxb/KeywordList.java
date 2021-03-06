package org.utils.jaxb;
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
	 @XmlElement(name = "keyword")
	    protected List<Keyword> keyword;
	    public List<Keyword> getKeyword() {
	        if (keyword == null) {
	           keyword = new ArrayList<Keyword>();
	        }
	        return this.keyword;
	    }
}
