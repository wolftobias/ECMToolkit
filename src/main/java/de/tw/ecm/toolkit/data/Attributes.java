package main.java.de.tw.ecm.toolkit.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Attributes implements Iterable<Attribute> {
	private List<Attribute> cache = new ArrayList<>();

	public Attributes() {
	}

	public Attributes(List<Attribute> attributes) {
		this.cache = attributes;
	}

	public Attribute getByName(String caption) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getName().equals(caption))
				return cache.get(i);
		}

		return null;
	}

	@XmlElement(name = "attribute")
	public List<Attribute> getAttributes() {
		return this.cache;
	}

	public void add(Attribute attribute) {
		this.cache.add(attribute);
	}

	public int size() {
		return this.cache.size();
	}

	public Attribute get(int i) {
		return this.cache.get(i);
	}

	public String[] getCaptions() {
		String[] captions = new String[this.size()];
		for (int i = 0; i < captions.length; i++) {
			captions[i] = this.get(i).getCaption().getText();
		}

		return captions;
	}

	public String[] getNames() {
		String[] names = new String[this.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = this.get(i).getName();
		}

		return names;
	}

	@Override
	public Iterator<Attribute> iterator() {
		return this.cache.iterator();
	}
}