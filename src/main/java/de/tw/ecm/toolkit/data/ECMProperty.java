package main.java.de.tw.ecm.toolkit.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlType(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
public class ECMProperty {
	
	@XmlAttribute
	private String key;
	
	@XmlValue
	private String value;

	public ECMProperty() {
	}

	public ECMProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}