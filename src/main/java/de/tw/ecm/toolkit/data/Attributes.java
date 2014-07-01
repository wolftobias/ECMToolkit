package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;

@XmlType
public class Attributes extends Values<Attribute> {
	
	public Attributes() {
	}

	public Attributes(List<Attribute> attributes) {
		this.values = attributes;
	}

	public Attribute getByName(String name) {
		for (Attribute attribute : values) {
			if (attribute.getName().equals(name))
				return attribute;
		}

		return null;
	}

	public Attribute getByCaption(String caption) {
		for (Attribute attribute : values) {
			if (attribute.getCaption().equalsIgnoreLocale(caption))
				return attribute;
		}

		return null;
	}

	@XmlElement(name = "attribute")
	public List<Attribute> getAttributes() {
		return this.values;
	}

	public List<Caption> getCaptions() {
		List<Caption> captions = new ArrayList<>();
		for (Attribute attribute : values) {
			captions.add(attribute.getCaption());
		}
		return captions;
	}

	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		for (Attribute attribute : values) {
			names.add(attribute.getName());
		}
		return names;
	}

	public Caption getCaptionByName(String name) {
		for (Attribute attribute : values) {
			if(attribute.getName().equalsIgnoreCase(name))
				return attribute.getCaption();
		}
		
		return null;
	}

	public String getNameByName(String name) {
		for (Attribute attribute : values) {
			if(attribute.getName().equalsIgnoreCase(name))
				return attribute.getName();
		}
		
		return null;
	}
}