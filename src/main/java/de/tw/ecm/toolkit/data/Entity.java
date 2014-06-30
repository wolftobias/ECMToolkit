package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;

@XmlType(propOrder = { "id", "caption", "attributes" })
public class Entity {

	private Caption caption;

	private String id;

	private Attributes attributes = new Attributes();
	
	private List<String> primaryKeys = new ArrayList<>();
	
	public Entity() {
	}

	public Entity(String id) {
		this.id = id;
	}

	@XmlElement(name = "caption")
	public Caption getCaption() {
		return caption;
	}

	public void setCaption(Caption caption) {
		this.caption = caption;
	}

	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlElement(name = "attributes")
	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute) {
		this.attributes.add(attribute);
	}

	public DataList newList() {
		return new DataList(this, new DataHeader(this.getAttributes().getNames(), this.getAttributes().getCaptions()));
	}
	
	@XmlTransient
	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(List<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	
	
}
