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
    "id"
})
@XmlRootElement(name = "ArtickeIdList")
public class ArticleIdList {
	


	    @XmlElement(name = "Id")
	    protected List<ArticleId> id;
	    public List<ArticleId> getId() {
	        if (id == null) {
	            id = new ArrayList<ArticleId>();
	        }
	        return this.id;
	    }

}
