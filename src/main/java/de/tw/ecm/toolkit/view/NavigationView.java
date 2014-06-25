package main.java.de.tw.ecm.toolkit.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "views" })
public class NavigationView extends BaseView implements Iterable<ContentView> {

	private ArrayList<ContentView> cache = new ArrayList<>();

	public NavigationView() {
	}

	public void add(ContentView View) {
		this.cache.add(View);
	}

	public int size() {
		return this.cache.size();
	}

	public ContentView get(int i) {
		return this.cache.get(i);
	}

	public ContentView getById(String id) {
		for (ContentView view : this.cache) {
			if (view.getId().equals(id))
				return view;
		}

		return null;
	}

	@XmlElementWrapper(name = "contentviews")
	@XmlElement(name = "contentview")
	public List<ContentView> getViews() {
		return this.cache;
	}

	public ContentView getDefaultContentView() {
		return this.getById(this.defaultView);
	}

	@Override
	public Iterator<ContentView> iterator() {
		return this.cache.iterator();
	}

	@Override
	public NavigationView build() {
		try {
			this.controllerClass = Class.forName(this.controller);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		for (ContentView contentView : this.cache) {
			contentView.build();
		}

		return this;
	}
}