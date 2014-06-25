package main.java.de.tw.ecm.toolkit.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.Builder;

@XmlRootElement(name = "views")
public class Views implements Builder<Views> {

	private ArrayList<View> cache = new ArrayList<>();

	private String defaultView;

	public Views() {
	}

	public Views(ArrayList<View> view) {
		this.cache = view;
	}

	public View getById(String id) {
		for (int i = 0; i < cache.size(); i++) {
			if (cache.get(i).getId().equals(id))
				return cache.get(i);
		}

		return null;
	}
	
	@XmlElement(name = "view")
	public List<View> getViews() {
		return this.cache;
	}

	public void add(View View) {
		this.cache.add(View);
	}

	public int size() {
		return this.cache.size();
	}

	public View get(int i) {
		return this.cache.get(i);
	}

	public View getDefaultView() {
		return this.getById(this.defaultView);
	}

	@XmlAttribute
	public String getDefault() {
		return this.defaultView;
	}

	public void setDefault(String defaultView) {
		this.defaultView = defaultView;
	}

	public String toXml() {
		String xml = "";
		JAXB.marshal(this, xml);
		return xml;
	}

	@Override
	public Views build() {
		return JAXB.unmarshal(new File("./systemPrefs.views.xml"), Views.class);
	}
}
