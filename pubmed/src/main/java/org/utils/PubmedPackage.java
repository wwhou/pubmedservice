package org.utils;

import java.io.InputStream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.http.entity.InputStreamEntity;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "type", "content" })
@XmlRootElement(name = "pubmedPackage")
public class PubmedPackage {

	@XmlElement(name = "type", required = true)
	protected String type;
	@XmlElement(name = "content", required = true)
	protected  InputStreamEntity content;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InputStreamEntity getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = new InputStreamEntity(content);
	}
}
