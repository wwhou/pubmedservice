package org.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "article" })
@XmlRootElement(name = "articleList")
public class ArticleList {

	@XmlElement(name="articleList", required=true)
	protected List<Article> article;
	
	public List<Article> getArticle(){
		if(article==null)
			article=new ArrayList<Article>();
		return article;
	}
}
