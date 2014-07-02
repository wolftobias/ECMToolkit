package main.java.de.tw.ecm.toolkit.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "views")
public class Views extends BaseViews<View> {

	private String defaultView;

	public Views() {
	}

	public Views(ArrayList<View> view) {
		this.values = view;
	}

	public View getById(String id) {
		for (View view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElement(name = "view")
	public List<View> getViews() {
		return this.values;
	}

	public View getDefaultView() {
		return this.getById(this.defaultView);
	}

	@Override
	public Views build() {
		Views views = JAXB.unmarshal(new File("./systemPrefs.views.xml"),
				Views.class);
		
		for (View view : views) {
			view.build();
		}

		return views;

	}
}
