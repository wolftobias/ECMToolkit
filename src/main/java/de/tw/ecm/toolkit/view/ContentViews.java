package main.java.de.tw.ecm.toolkit.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ContentViews extends BaseViews<ContentView> {

	private String defaultView;

	public ContentViews() {
	}

	public ContentViews(List<ContentView> view) {
		this.values = view;
	}

	public ContentView getById(String id) {
		for (ContentView view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElement(name = "view")
	public List<ContentView> getViews() {
		return this.values;
	}

	public ContentView getDefaultView() {
		return this.getById(this.defaultView);
	}

	@Override
	@XmlAttribute
	public String getDefault() {
		return super.getDefault();
	}

	@Override
	public ContentViews build() {
		for (ContentView view : this.values) {
			view.build();
		}

		return this;
	}
}
