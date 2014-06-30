package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import main.java.de.tw.ecm.toolkit.data.Attribute.Caption;

@XmlType
public class Attributes implements Iterable<Attribute> {
	
	private List<Attribute> attributes = new ArrayList<>();

	public Attributes() {
	}

	public Attributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Attribute getByName(String name) {
		for (Attribute attribute : attributes) {
			if (attribute.getName().equals(name))
				return attribute;
		}

		return null;
	}

	public Attribute getByCaption(String caption) {
		for (Attribute attribute : attributes) {
			if (attribute.getCaption().equalsIgnoreLocale(caption))
				return attribute;
		}

		return null;
	}

	@XmlElement(name = "attribute")
	public List<Attribute> getAttributes() {
		return this.attributes;
	}

	public void add(Attribute attribute) {
		this.attributes.add(attribute);
	}

	public int size() {
		return this.attributes.size();
	}

	public Attribute get(int i) {
		return this.attributes.get(i);
	}

	public List<Caption> getCaptions() {
		List<Caption> captions = new ArrayList<>();
		for (Attribute attribute : attributes) {
			captions.add(attribute.getCaption());
		}
		return captions;
	}

	public List<String> getNames() {
		List<String> names = new ArrayList<>();
		for (Attribute attribute : attributes) {
			names.add(attribute.getName());
		}
		return names;
	}

	public Caption getCaptionByName(String name) {
		for (Attribute attribute : attributes) {
			if(attribute.getName().equalsIgnoreCase(name))
				return attribute.getCaption();
		}
		
		return null;
	}

	public String getNameByName(String name) {
		for (Attribute attribute : attributes) {
			if(attribute.getName().equalsIgnoreCase(name))
				return attribute.getName();
		}
		
		return null;
	}

	@Override
	public Iterator<Attribute> iterator() {
		return this.attributes.iterator();
	}
}