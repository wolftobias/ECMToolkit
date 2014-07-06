package main.java.de.tw.ecm.toolkit.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class NavigationViews extends BaseViews<NavigationView> {

	public NavigationViews() {
	}

	public NavigationViews(ArrayList<NavigationView> view) {
		this.values = view;
	}

	public NavigationView getById(String id) {
		for (NavigationView view : this.values) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlAttribute
	public String getDefault() {
		return defaultView;
	}

	@XmlElement(name = "navigationView")
	public List<NavigationView> getViews() {
		return this.values;
	}

	public NavigationView getDefaultView() {
		return this.getById(this.defaultView);
	}

	@Override
	public NavigationViews build() {
		for (NavigationView view : values) {
			view.build();
		}

		return this;
	}
}
